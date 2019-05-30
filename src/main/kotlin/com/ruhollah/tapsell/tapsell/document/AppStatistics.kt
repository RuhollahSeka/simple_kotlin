package com.ruhollah.tapsell.tapsell.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

@Document
class AppStatistics(@Id val id: AppStatisticsKey,
                    var videoRequests: Int,
                    var webViewRequest: Int,
                    var videoClicks: Int,
                    var webViewClicks: Int,
                    var videoInstalls: Int,
                    var webViewInstalls: Int) : Serializable
{

    class AppStatisticsKey(val id: String,
                           val reportTime: Date,
                           val type: Int) : Serializable
}