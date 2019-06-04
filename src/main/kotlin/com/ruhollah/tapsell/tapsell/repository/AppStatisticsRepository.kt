package com.ruhollah.tapsell.tapsell.repository

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AppStatisticsRepository : MongoRepository<AppStatistics, String>, AppStatisticsRepositoryCustom
