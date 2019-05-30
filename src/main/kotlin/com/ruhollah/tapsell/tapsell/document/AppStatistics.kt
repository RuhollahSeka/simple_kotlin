package com.ruhollah.tapsell.tapsell.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class AppStatistics(@Id val id: AppStatisticsKey,
                    val videoRequests: Int,
                    val webViewRequest: Int,
                    val videoClicks: Int,
                    val webViewClicks: Int,
                    val videoInstalls: Int,
                    val webViewInstalls: Int)
{
//    @Id
//    val id: AppStatisticsKey
//
//    constructor(id: String, reportTime: Date, type: Int, videoRequests: Int, webViewInstalls: Int, videoClicks: Int,
//                webViewClicks: Int, videoInstalls: Int, webViewInstalls: Int)
//    {
//        this.id = AppStatisticsKey(id, reportTime, type)
//
//    }
//
//    init
//    {
//        this.id = AppStatisticsKey(id, reportTime, type)
//
//    }

    class AppStatisticsKey(val id: String,
                           val reportTime: Date,
                           val type: Int)
}