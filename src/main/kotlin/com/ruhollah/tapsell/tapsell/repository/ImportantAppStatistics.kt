package com.ruhollah.tapsell.tapsell.repository

import java.io.Serializable
import java.util.*

class ImportantAppStatistics(val reportTime: Date,
                             var videoRequests: Int,
                             var webViewRequest: Int,
                             var videoClicks: Int,
                             var webViewClicks: Int,
                             var videoInstalls: Int,
                             var webViewInstalls: Int): Serializable
