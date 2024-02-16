package ru.studiotg.minesweeper.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import ru.studiotg.minesweeper.model.Game;
import ru.studiotg.minesweeper.model.Id;

@Mapper
public interface GameRepository {

    @Select("INSERT INTO Games(width, height, mines_count, field) " +
            "VALUES('${width}', '${height}', '${minesCount}', '${field}') RETURNING game_id;")
    Id save(Game game);
}
