package edu.lewisu.hartke.FinalProject.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var date: Date,
    val score: String,
    val strikes: String,
    val spares: String,
    val openFrames: String,
    val notes: String
)



