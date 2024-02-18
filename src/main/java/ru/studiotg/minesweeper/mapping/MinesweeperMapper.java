package ru.studiotg.minesweeper.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.studiotg.minesweeper.dto.GameInfoResponse;
import ru.studiotg.minesweeper.dto.NewGameRequest;
import ru.studiotg.minesweeper.model.Game;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MinesweeperMapper {

    public GameInfoResponse newGameRequestToGameInfoResponse(NewGameRequest newGameRequest, List<List<String>> field, UUID gameId) {
        GameInfoResponse gameInfoResponse = new GameInfoResponse();
        gameInfoResponse.setGameId(gameId);
        gameInfoResponse.setWidth(newGameRequest.getWidth());
        gameInfoResponse.setHeight(newGameRequest.getHeight());
        gameInfoResponse.setMinesCount(newGameRequest.getMinesCount());
        gameInfoResponse.setCompleted(false);
        gameInfoResponse.setField(field);

        return gameInfoResponse;
    }

    public Game newGameRequestToGame(NewGameRequest newGameRequest, String field, String currentField) {
        Game game = new Game();
        game.setWidth(newGameRequest.getWidth());
        game.setHeight(newGameRequest.getHeight());
        game.setMinesCount(newGameRequest.getMinesCount());
        game.setField(field);
        game.setCurrentField(currentField);

        return game;
    }

    public GameInfoResponse gameToGameInfoResponse(Game game, List<List<String>> field) {
        GameInfoResponse gameInfoResponse = new GameInfoResponse();
        gameInfoResponse.setGameId(game.getGameId());
        gameInfoResponse.setWidth(game.getWidth());
        gameInfoResponse.setHeight(game.getHeight());
        gameInfoResponse.setMinesCount(game.getMinesCount());
        gameInfoResponse.setCompleted(game.getCompleted());
        gameInfoResponse.setField(field);

        return gameInfoResponse;
    }
}
