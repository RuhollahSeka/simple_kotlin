package com.ruhollah.tapsell.tapsell.repository

import java.util.*

interface AppStatisticsRepositoryCustom
{
    fun findStatsInOrder(type: Int, startDate: Date, endDate: Date): List<ImportantAppStatistics>
}