package com.ruhollah.tapsell.tapsell.response

import com.ruhollah.tapsell.tapsell.repository.ImportantAppStatistics
import java.io.Serializable

class AppStatisticsListResponse : Serializable
{
    val stats: MutableList<AppStatisticsModel> = mutableListOf()

    fun addStat(stat: ImportantAppStatistics)
    {
        if (stats.size == 0 || !stats.last().sameWeekWith(stat))
            stats.add(AppStatisticsModel(stat))
        else
            stats.last().updateStats(stat)
    }
}