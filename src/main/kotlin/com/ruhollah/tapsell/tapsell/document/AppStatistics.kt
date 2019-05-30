package com.ruhollah.tapsell.tapsell.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

@Document(collection = "AppStats")
class AppStatistics(@Id val id: String,
                    val reportTime: Date,
                    val type: Int,
                    var videoRequests: Int,
                    var webViewRequest: Int,
                    var videoClicks: Int,
                    var webViewClicks: Int,
                    var videoInstalls: Int,
                    var webViewInstalls: Int) : Serializable
