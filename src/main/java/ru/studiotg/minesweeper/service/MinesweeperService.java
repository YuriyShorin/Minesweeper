package ru.studiotg.minesweeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.studiotg.minesweeper.dto.ErrorResponse;
import ru.studiotg.minesweeper.dto.GameInfoResponse;
import ru.studiotg.minesweeper.dto.GameTurnRequest;
import ru.studiotg.minesweeper.dto.NewGameRequest;
import ru.studiotg.minesweeper.mapping.MinesweeperMapper;
import ru.studiotg.minesweeper.model.FieldState;
import ru.studiotg.minesweeper.model.Game;
import ru.studiotg.minesweeper.model.Id;
import ru.studiotg.minesweeper.repository.GameRepository;
import ru.studiotg.minesweeper.utils.MinesweeperGamePerformer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinesweeperService {

    private final GameRepository gameRepository;

    private final MinesweeperMapper minesweeperMapper;

    private final MinesweeperGamePerformer minesweeperGamePerformer;

    public ResponseEntity<?> newGame(NewGameRequest newGameRequest) {
        if (newGameRequest.getMinesCount() > newGameRequest.getWidth() * newGameRequest.getHeight() - 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Слишком много мин, должно быть хотя бы одно свободное поле"));
        }

        List<List<String>> field = minesweeperGamePerformer.generateField(newGameRequest);
        String stringField = minesweeperGamePerformer.fieldToString(field, newGameRequest.getWidth(), newGameRequest.getHeight());

        List<List<String>> unopenedField = minesweeperGamePerformer.createUnopenedField(newGameRequest);
        String unopenedFieldString = minesweeperGamePerformer.fieldToString(unopenedField, newGameRequest.getWidth(), newGameRequest.getHeight());

        Game game = minesweeperMapper.newGameRequestToGame(newGameRequest, stringField, unopenedFieldString);
        Id gameId = gameRepository.save(game);

        GameInfoResponse gameInfoResponse = minesweeperMapper.newGameRequestToGameInfoResponse(newGameRequest, unopenedField, gameId.getId());
        return ResponseEntity.ok(gameInfoResponse);
    }

    public ResponseEntity<?> nextTurn(GameTurnRequest gameTurnRequest) {
        Game game = gameRepository.findById(gameTurnRequest.getGameId());
        if (game == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ErrorResponse("Игры с таким идентификатором не существует"));
        }

        if (game.getCompleted()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Игра была завершена"));
        }

        if (gameTurnRequest.getRow() >= game.getHeight()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Строка должна быть в диапазоне от 0 до " + (game.getHeight() - 1)));
        }

        if (gameTurnRequest.getColumn() >= game.getWidth()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Столбец должен быть в диапазоне от 0 до " + (game.getWidth() - 1)));
        }

        List<List<String>> currentField = minesweeperGamePerformer.stringToField(game.getCurrentField(), game.getWidth(), game.getHeight());
        if (!currentField.get(gameTurnRequest.getRow()).get(gameTurnRequest.getColumn()).equals(FieldState.UNOPENED.getState())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Поле уже было открыто"));
        }

        List<List<String>> field = minesweeperGamePerformer.stringToField(game.getField(), game.getWidth(), game.getHeight());
        boolean isGameCompleted = minesweeperGamePerformer.performTurn(field, currentField, gameTurnRequest);

        game.setCompleted(isGameCompleted);
        String currentFieldString = minesweeperGamePerformer.fieldToString(currentField, game.getWidth(), game.getHeight());
        game.setCurrentField(currentFieldString);
        gameRepository.updateCurrentField(game);

        GameInfoResponse gameInfoResponse = minesweeperMapper.gameToGameInfoResponse(game, currentField);
        return ResponseEntity.ok(gameInfoResponse);
    }
}
