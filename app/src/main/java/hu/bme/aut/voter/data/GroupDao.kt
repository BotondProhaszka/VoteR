package hu.bme.aut.voter.data

import androidx.room.*
import hu.bme.aut.voter.model.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups")
    fun getOwnGroups(): List<Group>

    @Query("SELECT * FROM groups where id = :id")
    fun getGroupById(id: Long) : Group?

    @Insert
    fun insertGroup(group: Group) : Long

    @Delete
    fun deleteGroup(group : Group)
}