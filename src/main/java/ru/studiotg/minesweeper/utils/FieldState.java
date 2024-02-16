package ru.studiotg.minesweeper.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FieldState {

    UNOPENED(" "),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    MINE("X"),
    MINE_AFTER_WIN("M");

    private final String state;
}
