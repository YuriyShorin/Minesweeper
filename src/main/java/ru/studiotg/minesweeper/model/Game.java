package ru.studiotg.minesweeper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Game {

    private UUID gameId;

    private Integer width;

    private Integer height;

    private Integer minesCount;

    private Boolean completed;

    private String field;

    private String currentField;
}
