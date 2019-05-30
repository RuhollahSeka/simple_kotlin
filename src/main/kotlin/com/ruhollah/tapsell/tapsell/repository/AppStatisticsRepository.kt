package com.ruhollah.tapsell.tapsell.repository

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppStatisticsRepository : MongoRepository<AppStatistics, AppStatistics.AppStatisticsKey>
{
    fun findById_ReportTimeBetweenOrderById_ReportTimeAsc(startDate: Date, endDate: Date) : List<AppStatistics>

    fun findById_ReportTimeBetween(startDate: Date, endDate: Date) : List<AppStatistics>
}