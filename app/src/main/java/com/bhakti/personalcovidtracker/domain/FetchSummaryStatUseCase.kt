package com.bhakti.personalcovidtracker.domain

import com.bhakti.personalcovidtracker.data.JSONSummary
import com.bhakti.personalcovidtracker.service.NovelCoronaApiClient

/**
 * Fetch latest global stats & summary data for COVID 19 through API
 *
 * @author Bhakti
 */
class FetchSummaryStatUseCase constructor(private val api: NovelCoronaApiClient) {

    suspend fun execute(): JSONSummary? {
        return try {
            api.getSummary()
        } catch (ex: Throwable) {
            return null
        }
    }

}