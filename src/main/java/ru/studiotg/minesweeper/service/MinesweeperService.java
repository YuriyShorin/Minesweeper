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

        Game game = minesweeperMapper.newGameRequestToGame(newGameRequest, stringField);
        Id gameId = gameRepository.save(game);

        GameInfoResponse gameInfoResponse = minesweeperMapper.newGameRequestToGameInfoResponse(newGameRequest, field, gameId.getId());
        return ResponseEntity.ok(gameInfoResponse);
    }

    public ResponseEntity<?> nextTurn(GameTurnRequest gameTurnRequest) {
        return ResponseEntity.ok(new GameInfoResponse());
    }
}
