package hu.bme.aut.voter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey val gameName: String = "",
    @ColumnInfo(name = "gameIsSelected") var gameIsSelected: Boolean = true,
    @ColumnInfo(name = "gameIconId") val gameIconId: String = "")