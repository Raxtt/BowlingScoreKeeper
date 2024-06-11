package edu.lewisu.hartke.FinalProject.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.lewisu.hartke.FinalProject.DateConverter
import edu.lewisu.hartke.FinalProject.Model.Game

@Database(entities = [Game::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao


}