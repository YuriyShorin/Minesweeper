package ru.studiotg.minesweeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Id {

    private UUID id;
}
