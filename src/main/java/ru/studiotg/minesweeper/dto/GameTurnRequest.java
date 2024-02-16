package ru.studiotg.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Dto для описания хода")
@Data
public class GameTurnRequest {

    @Schema(description = "Идентификатор игры", example = "01234567-89AB-CDEF-0123-456789ABCDEF")
    @JsonProperty("game_id")
    private UUID gameId;

    @Schema(description = "Колонка проверяемой ячейки (нумерация с нуля)", example = "5")
    @JsonProperty("col")
    @Min(value = 0, message = "Нумерация колонок начинается с нуля")
    private Integer column;

    @Schema(description = "Ряд проверяемой ячейки (нумерация с нуля)", example = "5")
    @Min(value = 0, message = "Нумерация ячеек начинается с нуля")
    private Integer row;
}
