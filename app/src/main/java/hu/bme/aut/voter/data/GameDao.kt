package hu.bme.aut.voter.data

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.voter.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT  * FROM game WHERE gameIsSelected = true ")
    fun getSelectedGames() : LiveData<List<Game>>

    @Query("SELECT * FROM game WHERE gameName = :gameName")
    fun getGame(gameName: String) : Game?

    @Insert
    fun insertGame(game: Game) : Long

    @Update
    fun update(game: Game)

    @Delete
    fun deleteGame(game: Game)
}