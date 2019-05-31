package com.ruhollah.tapsell.tapsell

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import com.ruhollah.tapsell.tapsell.config.MongoConfig
import com.ruhollah.tapsell.tapsell.document.AppStatistics
import com.ruhollah.tapsell.tapsell.repository.AppStatisticsRepository
import com.ruhollah.tapsell.tapsell.response.AppStatisticsListResponse
import com.ruhollah.tapsell.tapsell.response.AppStatisticsModel
import com.ruhollah.tapsell.tapsell.service.AppStatisticsService
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.text.SimpleDateFormat

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [MongoConfig::class])
@WebMvcTest
class AppUnitTest
{
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var repository: AppStatisticsRepository

    @MockBean
    lateinit var service: AppStatisticsService

    @Test
    fun contextLoads()
    {
        val format = SimpleDateFormat("MM/dd/yyyy")
        val date1 = format.parse("01/01/2006")
        val date2 = format.parse("05/04/2020")

        Mockito.`when`(repository.findAll()).thenReturn(
                listOf(AppStatistics("app2", date1, 1, 0, 1, 2,
                        3, 1, 1),
                        AppStatistics("app", date2, 1, 3, 2, 1,
                                1, 3, 4))
        )

        val rawResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/rawstats").accept(MediaType.APPLICATION_JSON))
        rawResultActions.andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", `is`("app2")))
                .andExpect(jsonPath("$[1].id", `is`("app")))
                .andReturn()
    }

    @Test
    fun testService()
    {
        val locale = ULocale("fa_IR@calendar=persian")
        val format = SimpleDateFormat("MM/dd/yyyy")
        val date1 = format.parse("01/01/2006")
        val date2 = format.parse("05/04/2020")
        val calendar1 = Calendar.getInstance(locale)
        calendar1.timeInMillis = date1.time
        val weekNum1 = ((calendar1[Calendar.DAY_OF_YEAR] - 1) / 7) + 1
        val calendar2 = Calendar.getInstance(locale)
        calendar2.timeInMillis = date2.time
        val weekNum2 = ((calendar2[Calendar.DAY_OF_YEAR] - 1) / 7) + 1

        val appResponse = AppStatisticsListResponse()
        val model1 = AppStatisticsModel(AppStatistics("", date1, 1, 1, 1, 1,
                1, 1, 1))
        val model2 = AppStatisticsModel(AppStatistics("", date2, 1, 1, 1, 1,
                1, 1, 1))
        appResponse.stats.add(model1)
        appResponse.stats.add(model2)

        Mockito.`when`(service.getStats(format.parse("01/01/2000"), format.parse("01/01/2100"), 1))
                .thenReturn(
                        appResponse
                )

        val statsResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/stats")
                .param("type", "1").param("startDate", "01/01/2000")
                .param("endDate", "01/01/2100").accept(MediaType.APPLICATION_JSON))
        statsResultActions.andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stats[0].weekNum", `is`(weekNum1)))
                .andExpect(jsonPath("$.stats[0].year", `is`(calendar1[Calendar.YEAR])))
                .andExpect(jsonPath("$.stats[0].requests", `is`(2)))
                .andExpect(jsonPath("$.stats[0].clicks", `is`(2)))
                .andExpect(jsonPath("$.stats[0].installs", `is`(2)))
                .andExpect(jsonPath("$.stats[1].weekNum", `is`(weekNum2)))
                .andExpect(jsonPath("$.stats[1].year", `is`(calendar2[Calendar.YEAR])))
                .andExpect(jsonPath("$.stats[1].requests", `is`(2)))
                .andExpect(jsonPath("$.stats[1].clicks", `is`(2)))
                .andExpect(jsonPath("$.stats[1].installs", `is`(2)))
    }

}
