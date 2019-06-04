package com.ruhollah.tapsell.tapsell.repository

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.stereotype.Component
import java.util.*

@Component
class AppStatisticsRepositoryImpl : AppStatisticsRepositoryCustom
{
    @Autowired
    private lateinit var operations: MongoOperations

    override fun findStatsInOrder(type: Int, startDate: Date, endDate: Date): List<ImportantAppStatistics>
    {
        val aggregation = newAggregation(AppStatistics::class.java, match(where("type").`is`(type)),
                match(where("reportTime").gte(startDate)), match(where("reportTime").lte(endDate)),
                sort(Sort(Sort.Direction.ASC, "reportTime")))
        val results = operations.aggregate(aggregation, ImportantAppStatistics::class.java)
        return results.mappedResults
    }

}