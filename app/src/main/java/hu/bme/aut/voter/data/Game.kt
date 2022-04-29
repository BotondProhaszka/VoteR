package hu.bme.aut.voter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true) var gameId : Long?,
    @ColumnInfo(name = "gameName") val gameName: String,
    @ColumnInfo(name = "gameIsSelected") var gameIsSelected: Boolean,
    @ColumnInfo(name = "gameIconId") val gameIconId: String)