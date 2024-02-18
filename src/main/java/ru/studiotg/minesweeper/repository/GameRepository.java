package ru.studiotg.minesweeper.repository;

import org.apache.ibatis.annotations.*;
import ru.studiotg.minesweeper.model.Game;
import ru.studiotg.minesweeper.model.Id;

import java.util.UUID;

@Mapper
public interface GameRepository {

    @Results(value = {
            @Result(property = "id", column = "game_id")
    })
    @Select("INSERT INTO Games(width, height, mines_count, field, current_field) " +
            "VALUES('${width}', '${height}', '${minesCount}', '${field}', '${currentField}') RETURNING game_id;")
    Id save(Game game);

    @Results(value = {
            @Result(property = "gameId", column = "game_id"),
            @Result(property = "width", column = "width"),
            @Result(property = "height", column = "height"),
            @Result(property = "minesCount", column = "mines_count"),
            @Result(property = "completed", column = "completed"),
            @Result(property = "field", column = "field"),
            @Result(property = "currentField", column = "current_field")
    })
    @Select("SELECT * FROM GAMES " +
            "WHERE game_id = '${gameId}';")
    Game findById(UUID gameId);

    @Update("UPDATE Games " +
            "SET current_field = '${currentField}' " +
            "WHERE game_id = '${gameId}';")
    void updateCurrentField(Game game);
}
