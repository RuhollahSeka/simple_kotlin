package com.ruhollah.tapsell.tapsell.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class AppStatistics(@Id val key: CompositeKey,
                    val videoRequests: Int,
                    val webViewRequest: Int,
                    val videoClicks: Int,
                    val webViewClicks: Int,
                    val videoInstalls: Int,
                    val webViewInstalls: Int)
{
    inner class CompositeKey(val id: String,
                             val reportTime: Date,
                             val type: Int)
}