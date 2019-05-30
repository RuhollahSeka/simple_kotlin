package com.ruhollah.tapsell.tapsell.config

import com.ruhollah.tapsell.tapsell.repository.AppStatisticsRepository
import com.ruhollah.tapsell.tapsell.service.AppStatisticsService
import org.springframework.boot.CommandLineRunner
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.text.SimpleDateFormat

@Configuration
@EnableMongoRepositories(basePackageClasses = [AppStatisticsRepository::class])
@EnableCaching
class MongoConfig
{

    @Bean
    fun commandLineRunner(appStatisticsService: AppStatisticsService): CommandLineRunner
    {
        return CommandLineRunner {
//            val format = SimpleDateFormat("yyyy.MM.dd")
//            val date = format.parse("2019.01.01")
//            appStatisticsRepository.save(AppStatistics(AppStatistics.AppStatisticsKey("app4", date, 2),
//                    1, 1, 1, 1, 1, 1))
            val format = SimpleDateFormat("yyyy.MM.dd")
            val startDate = format.parse("2020.01.01")
            val endDate = format.parse("2018.01.01")
            val list = appStatisticsService.getStats(endDate, startDate, 1)
            list.stats.forEach { println("Year: ${it.year}, Week: ${it.weekNum}, Requests: ${it.requests}, " +
                    "Clicks: ${it.clicks}, Installs: ${it.installs}") }
        }
    }

}