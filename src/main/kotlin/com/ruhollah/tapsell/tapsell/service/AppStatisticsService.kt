package com.ruhollah.tapsell.tapsell.service

import com.ruhollah.tapsell.tapsell.response.AppStatisticsListResponse
import org.springframework.stereotype.Service
import java.util.*


interface AppStatisticsService
{
    fun getStats(startDate: Date, endDate: Date, type: Int): AppStatisticsListResponse
}