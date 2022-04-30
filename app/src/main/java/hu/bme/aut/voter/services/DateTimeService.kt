package hu.bme.aut.voter.services

import java.time.*
import java.time.temporal.ChronoUnit

class DateTimeService {

    fun getCurrentUTCTime(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
    }

    fun getCurrentLocalTime(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(ZoneOffset.systemDefault().id))
    }

    fun localTimeToUtc(dateTime: LocalDateTime): LocalDateTime {
        return dateTime.atZone(ZoneId.of(ZoneOffset.UTC.id)).toLocalDateTime()
    }

    fun utcTimeToLocal(dateTime: LocalDateTime): LocalDateTime{
        return LocalDateTime.ofInstant(dateTime.toInstant(ZoneOffset.UTC), ZoneId.of(ZoneOffset.systemDefault().id))
    }

    fun addMinutesToCurrentUtcTime(minutes: Long) : LocalDateTime {
        return getCurrentUTCTime().plusMinutes(minutes)
    }

    fun getRemainingTime(fromTime : LocalDateTime, toTime : LocalDateTime) : Long {
        return ChronoUnit.MINUTES.between(fromTime, toTime)
    }

    fun convertTimeToString(localDateTime: LocalDateTime) : String {
        return localDateTime.toString()
    }
    fun convertStringToTime(string: String) : LocalDateTime {
        return LocalDateTime.parse(string)
    }
}