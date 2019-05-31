package com.ruhollah.tapsell.tapsell

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import com.ruhollah.tapsell.tapsell.config.MongoConfig
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockServletContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.text.SimpleDateFormat

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [MongoConfig::class])
@SpringBootTest(classes = [TapsellApplication::class]) // OMG
class MongoSliceIntegrationTest
{
    private lateinit var mockMvc: MockMvc
    private val format = SimpleDateFormat("MM/dd/yyyy")

    @Autowired
    lateinit var wac: WebApplicationContext

    @Test
    @Before
    fun before()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
        val resultActions = mutableListOf<ResultActions>()
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/save?id=app1&reptime=01/03/2019&type=1&vidreq=0&webreq=1&vidclick=2&webclick=3&vidins=1&webins=1").accept(MediaType.APPLICATION_JSON)))
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/save?id=app2&reptime=01/02/2019&type=1&vidreq=0&webreq=1&vidclick=2&webclick=3&vidins=1&webins=1").accept(MediaType.APPLICATION_JSON)))
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/save?id=app3&reptime=01/13/2019&type=2&vidreq=0&webreq=1&vidclick=2&webclick=3&vidins=1&webins=1").accept(MediaType.APPLICATION_JSON)))
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/save?id=app4&reptime=01/04/2019&type=2&vidreq=0&webreq=1&vidclick=2&webclick=3&vidins=1&webins=1").accept(MediaType.APPLICATION_JSON)))

        resultActions.forEach { it.andExpect(status().isOk).andReturn() }
    }

    @After
    @Test
    fun after()
    {
        val resultActions = mutableListOf<ResultActions>()
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/delete?id=app1").accept(MediaType.APPLICATION_JSON)))
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/delete?id=app2").accept(MediaType.APPLICATION_JSON)))
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/delete?id=app3").accept(MediaType.APPLICATION_JSON)))
        resultActions.add(mockMvc.perform(MockMvcRequestBuilders
                .get("/delete?id=app4").accept(MediaType.APPLICATION_JSON)))

        resultActions.forEach { it.andExpect(status().isOk).andReturn() }

        mockMvc.perform(MockMvcRequestBuilders.get("/rawstats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("[]")).andReturn()
    }

    @Test
    fun checkControllerNull()
    {
        val serviceContext = wac.servletContext
        assertNotNull(serviceContext)
        assertTrue(serviceContext is MockServletContext)
        assertNotNull(wac.getBean("appStatisticsController"))
    }

    @Test
    fun checkGetStats()
    {
        val locale = ULocale("fa_IR@calendar=persian")
        val date1 = format.parse("01/04/2019")
        val date2 = format.parse("01/13/2019")
        val calendar1 = Calendar.getInstance(locale)
        calendar1.timeInMillis = date1.time
        val weekNum1 = ((calendar1[Calendar.DAY_OF_YEAR] - 1) / 7) + 1
        val calendar2 = Calendar.getInstance(locale)
        calendar2.timeInMillis = date2.time
        val weekNum2 = ((calendar2[Calendar.DAY_OF_YEAR] - 1) / 7) + 1

        var resultAction = mockMvc.perform(MockMvcRequestBuilders
                .get("/stats?type=1&startDate=01/01/2018&endDate=01/01/2020")
                .accept(MediaType.APPLICATION_JSON))
        resultAction.andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stats[0].year", `is`(calendar1[Calendar.YEAR])))
                .andExpect(jsonPath("$.stats[0].weekNum", `is`(weekNum1)))
                .andExpect(jsonPath("$.stats[0].requests", `is`(2)))
                .andExpect(jsonPath("$.stats[0].installs", `is`(4)))
                .andExpect(jsonPath("$.stats[0].clicks", `is`(10))).andReturn()

        resultAction = mockMvc.perform(MockMvcRequestBuilders
                .get("/stats?type=2&startDate=01/01/2018&endDate=01/01/2020")
                .accept(MediaType.APPLICATION_JSON))
        resultAction.andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stats[0].year", `is`(calendar1[Calendar.YEAR])))
                .andExpect(jsonPath("$.stats[0].weekNum", `is`(weekNum1)))
                .andExpect(jsonPath("$.stats[0].requests", `is`(1)))
                .andExpect(jsonPath("$.stats[0].installs", `is`(2)))
                .andExpect(jsonPath("$.stats[0].clicks", `is`(5)))
                .andExpect(jsonPath("$.stats[1].year", `is`(calendar2[Calendar.YEAR])))
                .andExpect(jsonPath("$.stats[1].weekNum", `is`(weekNum2)))
                .andExpect(jsonPath("$.stats[1].requests", `is`(1)))
                .andExpect(jsonPath("$.stats[1].installs", `is`(2)))
                .andExpect(jsonPath("$.stats[1].clicks", `is`(5))).andReturn()
    }
}