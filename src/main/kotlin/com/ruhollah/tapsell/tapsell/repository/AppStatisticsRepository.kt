package com.ruhollah.tapsell.tapsell.repository

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppStatisticsRepository : MongoRepository<AppStatistics, AppStatistics.AppStatisticsKey>
{
    fun findById_TypeEqualsAndId_ReportTimeBetweenOrderById_ReportTimeAsc(type: Int, startDate: Date,
                                                                          endDate: Date) : List<AppStatistics>

}