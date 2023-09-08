package com.danotech.rinfo.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun timeAgo(from: LocalDateTime, to: LocalDateTime = LocalDateTime.now()): String {
    val duration = ChronoUnit.SECONDS.between(from, to)

    return when {
        duration < 60 -> "$duration seconds ago"
        duration < 3600 -> "${duration / 60} minutes ago"
        duration < 86400 -> "${duration / 3600} hours ago"
        duration == 86400L -> "1 day ago"
        else -> "${duration / 86400} days ago"
    }
}