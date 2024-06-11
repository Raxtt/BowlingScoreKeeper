package edu.lewisu.hartke.FinalProject.repo

import android.content.Context
import androidx.room.Room
import edu.lewisu.hartke.FinalProject.Model.Game

class GameRepository private constructor(context: Context) {
    companion object {
        private var instance: GameRepository? = null

        fun getInstance(context: Context): GameRepository{
            if (instance == null){
                instance = GameRepository(context)
            }
            return instance!!
        }
    }

    private val database: GameDatabase = Room.databaseBuilder(
        context.applicationContext,
        GameDatabase::class.java,
        "game.db"
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries().build()
    private val gameDao = database.gameDao()


    fun getAllGames(): List<Game>{
        return gameDao.getAllGames()
    }

    fun getGame(id: Int) : Game {
        return gameDao.getGame(id)
    }

    fun deleteGame(game: Game){
        gameDao.deleteGames(game)
    }

    fun deleteAllGames(){
        gameDao.deleteAll()
    }

    fun insertGame(game: Game){
        gameDao.insertGames(game)
    }

    fun updateGame(game: Game){
        gameDao.updateGames(game)
    }
}