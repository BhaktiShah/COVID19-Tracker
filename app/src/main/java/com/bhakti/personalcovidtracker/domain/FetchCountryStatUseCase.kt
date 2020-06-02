package com.bhakti.personalcovidtracker.domain

import com.bhakti.personalcovidtracker.data.JSONCountry
import com.bhakti.personalcovidtracker.service.NovelCoronaApiClient

/**
 * Use case to fetch live country stats for the given country through API
 *
 * @author Bhakti
 */
class FetchCountryStatUseCase constructor(private val api: NovelCoronaApiClient) {

    class Params(val country: String)

    suspend fun execute(params: Params): JSONCountry? {
        return try {
            api.getLiveCountryDetails(params.country)
        } catch (ex: Throwable) {
            null
        }

    }


}