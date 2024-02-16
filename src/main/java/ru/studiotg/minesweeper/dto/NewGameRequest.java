package ru.studiotg.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Schema(description = "Dto для создания игрового поля")
@Data
public class NewGameRequest {

    @Schema(description = "Ширина игрового поля", example = "10")
    @Min(value = 2, message = "Ширина поля должна быть не меньше 2")
    @Max(value = 30, message = "Ширина поля не может превышать 30")
    private Integer width;

    @Schema(description = "Высота игрового поля", example = "10")
    @Min(value = 2, message = "Высота поля должна быть не меньше 2")
    @Max(value = 30, message = "Высота поля не может превышать 30")
    private Integer height;

    @Schema(description = "Количество мин на поле", example = "10")
    @JsonProperty("mines_count")
    @Min(value = 1, message = "Количество мин должно быть не меньше 1")
    private Integer minesCount;
}
