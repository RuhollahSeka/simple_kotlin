package com.ruhollah.tapsell.tapsell.response

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import com.ruhollah.tapsell.tapsell.repository.ImportantAppStatistics
import java.io.Serializable

class AppStatisticsModel(stat: ImportantAppStatistics) : Serializable
{
    val weekNum: Int
    val year: Int
    var requests: Int = 0
    var clicks: Int = 0
    var installs: Int = 0

    init
    {
        val statDate = stat.reportTime
        val locale = ULocale("fa_IR@calendar=persian")
        val calendar = Calendar.getInstance(locale)
        calendar.timeInMillis = statDate.time
        this.year = calendar[Calendar.YEAR]
        this.weekNum = ((calendar[Calendar.DAY_OF_YEAR] - 1) / 7) + 1
        updateStats(stat)
    }

    fun updateStats(stat: ImportantAppStatistics)
    {
        this.requests += stat.videoRequests + stat.webViewRequest
        this.clicks += stat.videoClicks + stat.webViewClicks
        this.installs += stat.videoInstalls + stat.webViewInstalls
    }

    fun sameWeekWith(stat: ImportantAppStatistics): Boolean
    {
        val locale = ULocale("fa_IR@calendar=persian")
        val calendar = Calendar.getInstance(locale)
        calendar.timeInMillis = stat.reportTime.time
        return calendar[Calendar.YEAR] == this.year && ((calendar[Calendar.DAY_OF_YEAR] - 1) / 7) + 1 == this.weekNum
    }
}
