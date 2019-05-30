package com.ruhollah.tapsell.tapsell.repository

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppStatisticsRepository : MongoRepository<AppStatistics, String>
{
    fun findByTypeEqualsAndReportTimeBetweenOrderByReportTimeAsc(type: Int, startDate: Date,
                                                                 endDate: Date) : List<AppStatistics>

}