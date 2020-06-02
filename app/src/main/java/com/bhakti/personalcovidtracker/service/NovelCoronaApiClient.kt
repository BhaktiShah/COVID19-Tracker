package com.bhakti.personalcovidtracker.service

import com.bhakti.personalcovidtracker.Constants
import com.bhakti.personalcovidtracker.data.JSONCountry
import com.bhakti.personalcovidtracker.data.JSONSummary
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Client to process all APIs regarding COVID19
 * Uses retrofit with coroutine support to handle suspended responses
 *
 * @author Bhakti
 */
class NovelCoronaApiClient(retrofit: Retrofit) : ApiClient() {

    private val covid19Api: Covid19Api

    init {
        covid19Api = retrofit.create(Covid19Api::class.java)
    }

    suspend fun getSummary(): JSONSummary? {
        return covid19Api.summary()
    }

    suspend fun getCountries(): List<JSONCountry>? {//todo: use later
        return covid19Api.countries()
    }

    suspend fun getLiveCountryDetails(countrySlug: String): JSONCountry? {//todo : use later
        return covid19Api.liveCountryDetails(countrySlug)?.last()
    }

    private interface Covid19Api {
        @GET(Constants.COVID19_BASE_URL + "/summary")
        suspend fun summary(): JSONSummary?

        @GET(Constants.COVID19_BASE_URL + "/countries")
        suspend fun countries(): List<JSONCountry>?

        @GET(Constants.COVID19_BASE_URL + "/country/" + "{slug}")
        suspend fun liveCountryDetails(@Path("slug") slug: String): List<JSONCountry>?
    }
}