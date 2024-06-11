package edu.lewisu.hartke.FinalProject.repo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.lewisu.hartke.FinalProject.Model.Game


@Dao
interface GameDao {
    @Insert
    fun insertGames(vararg game: Game)

    @Update
    fun updateGames(vararg game: Game)

    @Delete
    fun deleteGames(vararg game: Game)

    @Query("DELETE FROM game")
    fun deleteAll()

    @Query("SELECT * FROM game where id= :id")
    fun getGame(id: Int) : Game

    @Query("SELECT * FROM game ORDER BY id")
    fun getAllGames() : List<Game>
}