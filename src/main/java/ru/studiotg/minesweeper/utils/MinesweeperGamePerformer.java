package ru.studiotg.minesweeper.utils;

import org.springframework.stereotype.Component;
import ru.studiotg.minesweeper.dto.GameTurnRequest;
import ru.studiotg.minesweeper.dto.NewGameRequest;
import ru.studiotg.minesweeper.model.FieldState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MinesweeperGamePerformer {

    public List<List<String>> generateField(NewGameRequest newGameRequest) {
        Random random = new Random();
        List<List<String>> field = createUnopenedField(newGameRequest);

        int minesCount = 0;
        while (minesCount != newGameRequest.getMinesCount()) {
            int row = random.nextInt(newGameRequest.getHeight());
            int column = random.nextInt(newGameRequest.getWidth());
            if (field.get(row).get(column).equals(FieldState.UNOPENED.getState())) {
                field.get(row).set(column, FieldState.MINE.getState());
                minesCount++;
            }
        }

        for (int row = 0; row < newGameRequest.getHeight(); ++row) {
            for (int column = 0; column < newGameRequest.getWidth(); ++column) {
                if (field.get(row).get(column).equals(FieldState.MINE.getState())) {
                    continue;
                }

                int minesAroundCounter = 0;
                if (row - 1 >= 0 && column - 1 >= 0 && field.get(row - 1).get(column - 1).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (row - 1 >= 0 && field.get(row - 1).get(column).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (row - 1 >= 0 && column + 1 < newGameRequest.getWidth() && field.get(row - 1).get(column + 1).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (column - 1 >= 0 && field.get(row).get(column - 1).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (column + 1 < newGameRequest.getWidth() && field.get(row).get(column + 1).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (row + 1 < newGameRequest.getHeight() && column - 1 >= 0 && field.get(row + 1).get(column - 1).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (row + 1 < newGameRequest.getHeight() && field.get(row + 1).get(column).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }
                if (row + 1 < newGameRequest.getHeight() && column + 1 < newGameRequest.getWidth() && field.get(row + 1).get(column + 1).equals(FieldState.MINE.getState())) {
                    minesAroundCounter++;
                }

                switch (minesAroundCounter) {
                    case 0 -> field.get(row).set(column, FieldState.ZERO.getState());
                    case 1 -> field.get(row).set(column, FieldState.ONE.getState());
                    case 2 -> field.get(row).set(column, FieldState.TWO.getState());
                    case 3 -> field.get(row).set(column, FieldState.THREE.getState());
                    case 4 -> field.get(row).set(column, FieldState.FOUR.getState());
                    case 5 -> field.get(row).set(column, FieldState.FIVE.getState());
                    case 6 -> field.get(row).set(column, FieldState.SIX.getState());
                    case 7 -> field.get(row).set(column, FieldState.SEVEN.getState());
                    case 8 -> field.get(row).set(column, FieldState.EIGHT.getState());
                }
            }
        }

        return field;
    }

    public List<List<String>> createUnopenedField(NewGameRequest newGameRequest) {
        List<List<String>> unopenedField = new ArrayList<>(newGameRequest.getHeight());
        for (int row = 0; row < newGameRequest.getHeight(); ++row) {
            unopenedField.add(new ArrayList<>(newGameRequest.getWidth()));
            for (int column = 0; column < newGameRequest.getWidth(); ++column) {
                unopenedField.get(row).add(FieldState.UNOPENED.getState());
            }
        }

        return unopenedField;
    }

    public boolean performTurn(List<List<String>> field, List<List<String>> currentField, GameTurnRequest gameTurnRequest) {
        switch (field.get(gameTurnRequest.getRow()).get(gameTurnRequest.getColumn())) {
            case "X" -> {
                currentField
                        .get(gameTurnRequest.getRow())
                        .set(gameTurnRequest.getColumn(), FieldState.MINE.getState());
                return true;
            }
            case "0" -> openZeroSector(field, currentField, gameTurnRequest.getRow(), gameTurnRequest.getColumn());
            case "1" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.ONE.getState());
            case "2" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.TWO.getState());
            case "3" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.THREE.getState());
            case "4" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.FOUR.getState());
            case "5" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.FIVE.getState());
            case "6" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.SIX.getState());
            case "7" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.SEVEN.getState());
            case "8" -> currentField
                    .get(gameTurnRequest.getRow())
                    .set(gameTurnRequest.getColumn(), FieldState.EIGHT.getState());
        }

        boolean isGameCompleted = isGameCompleted(field, currentField);
        if (isGameCompleted) {
            for (int row = 0; row < field.size(); ++row) {
                for (int column = 0; column < field.get(row).size(); ++column) {
                    if (field.get(row).get(column).equals(FieldState.MINE.getState())) {
                        field.get(row).set(column, FieldState.MINE_AFTER_WIN.getState());
                        currentField.get(row).set(column, FieldState.MINE_AFTER_WIN.getState());
                    }
                }
            }
        }

        return isGameCompleted;
    }

    private void openZeroSector(List<List<String>> field, List<List<String>> currentField, int row, int column) {
        if (row < 0 || row >= field.size() || column < 0 || column >= field.get(0).size()) {
            return;
        }

        if (field.get(row).get(column).equals(currentField.get(row).get(column))) {
            return;
        }

        currentField.get(row).set(column, field.get(row).get(column));
        if (!field.get(row).get(column).equals(FieldState.ZERO.getState())) {
            return;
        }

        openZeroSector(field, currentField, row - 1, column);
        openZeroSector(field, currentField, row, column - 1);
        openZeroSector(field, currentField, row, column + 1);
        openZeroSector(field, currentField, row + 1, column);
    }

    private boolean isGameCompleted(List<List<String>> field, List<List<String>> currentField) {
        for (int row = 0; row < field.size(); ++row) {
            for (int column = 0; column < field.get(row).size(); ++column) {
                if (!field.get(row).get(column).equals(FieldState.MINE.getState()) &&
                        currentField.get(row).get(column).equals(FieldState.UNOPENED.getState())) {
                    return false;
                }
            }
        }

        return true;
    }

    public String fieldToString(List<List<String>> field, int width, int height) {
        StringBuilder fieldBuilder = new StringBuilder();
        for (int row = 0; row < height; ++row) {
            for (int column = 0; column < width; ++column) {
                fieldBuilder.append(field.get(row).get(column));
            }
        }

        return fieldBuilder.toString();
    }

    public List<List<String>> stringToField(String stringField, int width, int height) {
        List<List<String>> field = new ArrayList<>(height);
        int stringIndex = 0;
        for (int row = 0; row < height; ++row) {
            field.add(new ArrayList<>(width));
            for (int column = 0; column < width; ++column) {
                field.get(row).add(String.valueOf(stringField.charAt(stringIndex)));
                stringIndex++;
            }
        }

        return field;
    }
}
