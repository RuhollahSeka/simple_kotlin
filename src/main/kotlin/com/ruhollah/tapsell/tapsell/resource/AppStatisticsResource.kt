package com.ruhollah.tapsell.tapsell.resource

import com.ruhollah.tapsell.tapsell.document.AppStatistics
import com.ruhollah.tapsell.tapsell.repository.AppStatisticsRepository
import com.ruhollah.tapsell.tapsell.response.AppStatisticsListResponse
import com.ruhollah.tapsell.tapsell.service.AppStatisticsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AppStatisticsResource @Autowired constructor(val repository: AppStatisticsRepository,
                                                   val service: AppStatisticsService)
{
    @RequestMapping("/stats", method = [RequestMethod.GET])
    fun getStats(@RequestParam("type") type: Int,
                 @RequestParam("startDate") @DateTimeFormat(pattern = "MM/dd/yyyy") startDate: Date,
                 @RequestParam("endDate") @DateTimeFormat(pattern = "MM/dd/yyyy") endDate: Date)
            :AppStatisticsListResponse
    {
        return service.getStats(startDate, endDate, type)
    }

    @RequestMapping("/rawstats", method = [RequestMethod.GET])
    fun getRawStats(): List<AppStatistics>
    {
        return repository.findAll()
    }

    @RequestMapping("/save")
    fun saveRawStat(@RequestParam("id") id: String,
                    @RequestParam("reptime") @DateTimeFormat(pattern = "MM/dd/yyyy") reportTime: Date,
                    @RequestParam("type") type: Int, @RequestParam("vidreq") videoRequests: Int,
                    @RequestParam("webreq") webViewRequests: Int, @RequestParam("vidclick") videoClicks: Int,
                    @RequestParam("webclick") webViewClicks: Int, @RequestParam("vidins") videoInstalls: Int,
                    @RequestParam("webins") webViewInstalls: Int)
    {
        val appRawStat = AppStatistics(id, reportTime, type, videoRequests, webViewRequests, videoClicks, webViewClicks,
                videoInstalls, webViewInstalls)
        repository.save(appRawStat)
    }

    @RequestMapping("/delete")
    fun deleteRawStat(@RequestParam("id") id: String)
    {
        repository.deleteById(id)
    }
}