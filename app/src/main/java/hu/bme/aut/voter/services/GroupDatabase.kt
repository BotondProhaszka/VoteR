package hu.bme.aut.voter.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.voter.data.GroupDao
import hu.bme.aut.voter.model.Group


@Database(entities = [Group::class], version = 1)
abstract class GroupDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    companion object {
        private lateinit var INSTANCE: GroupDatabase
        fun initDatabase(context: Context) {
            GroupDatabase.INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GroupDatabase::class.java, "group.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getInstance(): GroupDatabase = INSTANCE
    }
}