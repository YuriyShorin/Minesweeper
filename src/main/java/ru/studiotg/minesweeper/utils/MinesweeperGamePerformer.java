package ru.studiotg.minesweeper.utils;

import org.springframework.stereotype.Component;
import ru.studiotg.minesweeper.dto.NewGameRequest;
import ru.studiotg.minesweeper.model.FieldState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MinesweeperGamePerformer {

    public List<List<String>> generateField(NewGameRequest newGameRequest) {
        Random random = new Random();
        List<List<String>> field = new ArrayList<>(newGameRequest.getHeight());
        for (int row = 0; row < newGameRequest.getHeight(); ++row) {
            field.add(new ArrayList<>(newGameRequest.getWidth()));
            for (int column = 0; column < newGameRequest.getWidth(); ++column) {
                field.get(row).add(FieldState.UNOPENED.getState());
            }
        }

        int minesCount = 0;
        while (minesCount != newGameRequest.getMinesCount()) {
            int row = random.nextInt(newGameRequest.getHeight());
            int column = random.nextInt(newGameRequest.getWidth());
            if (field.get(row).get(column).equals(FieldState.UNOPENED.getState())) {
                field.get(row).set(column, FieldState.MINE.getState());
                minesCount++;
            }
        }

        return field;
    }

    public String fieldToString(List<List<String>> field, int width, int height) {
        StringBuilder fieldBuilder = new StringBuilder();
        for (int rows = 0; rows < height; ++rows) {
            for (int columns = 0; columns < width; ++columns) {
                fieldBuilder.append(field.get(rows).get(columns));
            }
        }

        return fieldBuilder.toString();
    }
}
