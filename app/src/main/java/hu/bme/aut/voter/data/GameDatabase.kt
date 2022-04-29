package hu.bme.aut.voter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Game::class], version = 1)
abstract class GameDatabase : RoomDatabase(){
    abstract fun gameDao() : GameDao

    companion object{
        private var INSTANCE : GameDatabase? = null

        fun getInstance(context: Context) : GameDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                GameDatabase::class.java, "game.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}