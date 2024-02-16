package ru.studiotg.minesweeper.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.studiotg.minesweeper.dto.GameInfoResponse;
import ru.studiotg.minesweeper.dto.GameTurnRequest;
import ru.studiotg.minesweeper.dto.NewGameRequest;

@Service
public class MinesweeperService {

    public ResponseEntity<GameInfoResponse> newGame(NewGameRequest newGameRequest) {
        return ResponseEntity.ok(new GameInfoResponse());
    }

    public ResponseEntity<GameInfoResponse> nextTurn(GameTurnRequest gameTurnRequest) {
        return ResponseEntity.ok(new GameInfoResponse());
    }
}
