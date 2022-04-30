package hu.bme.aut.voter.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.voter.model.Game
import hu.bme.aut.voter.data.GameDao

@Database(entities = [Game::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        private lateinit var INSTANCE: GameDatabase
        fun initDatabase(context: Context) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GameDatabase::class.java, "game.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getInstance(): GameDatabase {
            return INSTANCE
        }

    }

}