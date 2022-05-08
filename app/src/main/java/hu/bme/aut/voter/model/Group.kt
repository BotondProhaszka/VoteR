package hu.bme.aut.voter.model

import androidx.room.*
import hu.bme.aut.voter.interfaces.UserInterface

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "connectId") var connectId : String ="",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "ownerEmail") var ownerEmail: String = "",
    @Ignore var members : MutableList<UserInterface> = mutableListOf()
    )