package ru.studiotg.minesweeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.studiotg.minesweeper.dto.GameInfoResponse;
import ru.studiotg.minesweeper.dto.GameTurnRequest;
import ru.studiotg.minesweeper.dto.NewGameRequest;
import ru.studiotg.minesweeper.service.MinesweeperService;

@Tag(name = "Minesweeper controller", description = "АПИ игры Сапёр")
@RequestMapping("/api/v1/minesweeper")
@RestController
@RequiredArgsConstructor
public class MinesweeperController {

    private final MinesweeperService minesweeperService;

    @Operation(summary = "Начать новую игру")
    @ApiResponse(
            responseCode = "200",
            description = "Игра создана",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameInfoResponse.class))})
    @PostMapping("/new")
    public ResponseEntity<?> newGame(@RequestBody @Valid NewGameRequest newGameRequest) {
        return minesweeperService.newGame(newGameRequest);
    }

    @Operation(summary = "Сделать следующий ход")
    @ApiResponse(
            responseCode = "200",
            description = "Ход сделан",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameInfoResponse.class))})
    @PostMapping("/turn")
    public ResponseEntity<?> nextTurn(@RequestBody @Valid GameTurnRequest gameTurnRequest) {
        return minesweeperService.nextTurn(gameTurnRequest);
    }
}
