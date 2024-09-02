package com.gs.wialonlocal.features.monitoring.domain.model

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class UnitModel(
    val id: String,
    val carNumber: String,
    val number: String,
    val image: String,
    val lastOnlineTime: String,
    val address: String,
    val speed: String,
    val estimateTime: String,
    val estimateDistance: String,
    val isOnline: Boolean,
    var latitude: Double,
    var longitude: Double,
) {
    fun calculateDifference():Pair<String, Color> {
        val currentDate = Clock.System.now()

        // Convert long date (milliseconds since epoch) to Instant
        val pastDate = Instant.fromEpochSeconds(lastOnlineTime.toLong())
        val duration = currentDate - pastDate

        // Optionally, convert this duration to a more readable format
        val days = duration.inWholeDays
        val hours = (duration.inWholeHours % 24).toInt()
        val minutes = (duration.inWholeMinutes % 60).toInt()
        val seconds = (duration.inWholeSeconds % 60).toInt()

        return if(days>1){
            Pair("$days days", Color(0xFFDEDEDE))
        } else if(hours>1){
            Pair("$hours h", Color(0xFFFBE0D7))
        } else if(minutes>5){
            Pair("$minutes min", Color(0xFFFEF7DB))
        } else if(minutes>0) {
            Pair("$minutes min", Color(0xFFDFEEDD))
        } else {
            Pair("$seconds s", Color(0xFFDFEEDD))
        }
    }

    fun convertTime(): String {
        val currentDate = Clock.System.now()

        // Convert long date (milliseconds since epoch) to Instant
        val pastDate = Instant.fromEpochSeconds(estimateTime.toLong())
        val duration = currentDate - pastDate

        // Optionally, convert this duration to a more readable format
        val days = duration.inWholeDays
        val hours = (duration.inWholeHours % 24).toInt()
        val minutes = (duration.inWholeMinutes % 60).toInt()
        val seconds = (duration.inWholeSeconds % 60).toInt()

        var result = ""

        if(days>0){
            result = result.plus("$days d")
        } else if(hours>0){
            result = result.plus("$hours h")
        } else if(minutes>0){
            result = result.plus("$minutes min")
        } else if(seconds>0) {
            result = result.plus("$seconds s")
        }

        return result
    }
}
