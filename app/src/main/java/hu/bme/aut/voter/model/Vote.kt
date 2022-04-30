package hu.bme.aut.voter.model

import hu.bme.aut.voter.activities.MainActivity
import java.time.Instant
import java.time.LocalDateTime

data class Vote(val name : String = "", val ownerId : String = "", val games: List<Game> = listOf(), val deadline: String = MainActivity.dateTimeService.getCurrentUTCTime().toString()) {


}