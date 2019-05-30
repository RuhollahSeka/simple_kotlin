package com.ruhollah.tapsell.tapsell.config

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import com.ruhollah.tapsell.tapsell.repository.AppStatisticsRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Configuration
@EnableMongoRepositories(basePackageClasses = [AppStatisticsRepository::class])
class MongoConfig
{

    @Bean
    fun commandLineRunner(appStatisticsRepository: AppStatisticsRepository): CommandLineRunner
    {
        return CommandLineRunner {
//            val format = SimpleDateFormat("yyyy.MM.dd")
//            val date = format.parse("2019.01.01")
//            appStatisticsRepository.save(AppStatistics(AppStatistics.AppStatisticsKey("app4", date, 1),
//                    1, 1, 1, 1, 1, 1))
            val format = SimpleDateFormat("yyyy.MM.dd")
            val startDate = format.parse("2020.01.01")
            val endDate = format.parse("2018.01.01")
            val list = appStatisticsRepository.findById_ReportTimeBetweenOrderById_ReportTimeAsc(endDate, startDate)
//            val list = appStatisticsRepository.findById_ReportTimeBetween(endDate, startDate)
            println()
        }
    }

}