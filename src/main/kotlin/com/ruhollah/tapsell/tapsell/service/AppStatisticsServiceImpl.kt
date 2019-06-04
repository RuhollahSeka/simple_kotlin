package com.ruhollah.tapsell.tapsell.service

import com.ruhollah.tapsell.tapsell.repository.AppStatisticsRepository
import com.ruhollah.tapsell.tapsell.response.AppStatisticsListResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.util.*

@Component
class AppStatisticsServiceImpl @Autowired constructor(val repository: AppStatisticsRepository) : AppStatisticsService
{
    @Cacheable("statsCache")
    override fun getStats(startDate: Date, endDate: Date, type: Int): AppStatisticsListResponse
    {
        println("Not getting from cache")
        val resultStats = AppStatisticsListResponse()
        val rawStats = repository.findStatsInOrder(type, startDate, endDate)
        rawStats.forEach {resultStats.addStat(it)}
        return resultStats
    }
}