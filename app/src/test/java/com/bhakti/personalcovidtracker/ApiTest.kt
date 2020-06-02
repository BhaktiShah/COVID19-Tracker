package com.bhakti.personalcovidtracker

import com.bhakti.personalcovidtracker.data.JSONCountryDetail
import com.bhakti.personalcovidtracker.data.JSONGlobal
import com.bhakti.personalcovidtracker.data.JSONSummary
import com.bhakti.personalcovidtracker.service.NovelCoronaApiClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ApiTest {

    val api = mock(NovelCoronaApiClient::class.java)
    val expectedSummary = JSONSummary(JSONGlobal(2, 5, 0, 4, 6, 7), listOf(JSONCountryDetail("CountryName", "CC", "Slug", 3, 5, 1, 2, 3, 2, Date())), Date())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testSummary() {
        //val api = ApiFactory.provideCovid19Api()
        runBlocking {
            Mockito.`when`(api.getSummary()).thenReturn(expectedSummary)
            val res = api.getSummary()
            Assert.assertEquals(res, expectedSummary)
        }
    }

}