package ru.studiotg.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Schema(description = "Dto для отправки ответа с информацией о текущем состоянии игры")
@Data
public class GameInfoResponse {

    @Schema(description = "Идентификатор игры", example = "01234567-89AB-CDEF-0123-456789ABCDEF")
    @JsonProperty("game_id")
    private UUID gameId;

    @Schema(description = "Ширина игрового поля", example = "10")
    @JsonProperty("width")
    private Integer width;

    @Schema(description = "Высота игрового поля", example = "10")
    @JsonProperty("height")
    private Integer height;

    @Schema(description = "Количество мин на поле", example = "10")
    @JsonProperty("mines_count")
    private Integer minesCount;

    @Schema(description = "Завершена ли игра", example = "false")
    @JsonProperty("completed")
    private Boolean completed;

    @Schema(description = "Игровое поле")
    @JsonProperty("field")
    private List<List<String>> field;
}
