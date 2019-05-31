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
{
    override fun equals(other: Any?): Boolean
    {
        val otherStat: AppStatistics = other as AppStatistics
        return otherStat.id == this.id && otherStat.reportTime.equals(this.reportTime) && otherStat.type == this.type
                && otherStat.videoClicks == this.videoClicks && otherStat.videoInstalls == this.videoInstalls
                && otherStat.videoRequests == this.videoRequests && otherStat.webViewClicks == this.webViewClicks
                && otherStat.webViewInstalls == this.webViewInstalls && otherStat.webViewRequest == this.webViewRequest
    }

    override fun hashCode(): Int
    {
        var result = id.hashCode()
        result = 31 * result + reportTime.hashCode()
        result = 31 * result + type
        result = 31 * result + videoRequests
        result = 31 * result + webViewRequest
        result = 31 * result + videoClicks
        result = 31 * result + webViewClicks
        result = 31 * result + videoInstalls
        result = 31 * result + webViewInstalls
        return result
    }
}
