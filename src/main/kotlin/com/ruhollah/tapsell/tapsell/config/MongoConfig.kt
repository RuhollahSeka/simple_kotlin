package com.ruhollah.tapsell.tapsell.config

import com.ruhollah.tapsell.tapsell.controller.AppStatisticsController
import com.ruhollah.tapsell.tapsell.repository.AppStatisticsRepository
import com.ruhollah.tapsell.tapsell.service.AppStatisticsServiceImpl
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackageClasses = [AppStatisticsRepository::class])
@EnableCaching
@ComponentScan(basePackageClasses = [AppStatisticsServiceImpl::class, AppStatisticsController::class])
class MongoConfig
