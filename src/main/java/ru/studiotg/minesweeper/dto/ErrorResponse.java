package ru.studiotg.minesweeper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Dto для отправки ошибки")
@Data
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "Описание ошибки", example = "Произошла непредвиденная ошибка")
    private String error;
}
