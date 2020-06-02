package com.bhakti.personalcovidtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhakti.personalcovidtracker.domain.FetchCountryStatUseCase
import com.bhakti.personalcovidtracker.domain.FetchSummaryStatUseCase
import com.bhakti.personalcovidtracker.service.ApiFactory

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(OverviewViewModel::class.java) -> return getSummaryViewModel<T>()
            modelClass.isAssignableFrom(CountryDetailViewModel::class.java) -> return getDetailViewModel<T>()
        }
        throw IllegalArgumentException("ViewModelFactory cannot provide for type ${modelClass.name}")
    }

    private fun <T : ViewModel?> getSummaryViewModel(): T {
        val useCase = FetchSummaryStatUseCase(ApiFactory.provideCovid19Api())//inject
        return OverviewViewModel(useCase) as T
    }

    private fun <T : ViewModel?> getDetailViewModel(): T {
        val useCase = FetchCountryStatUseCase(ApiFactory.provideCovid19Api())//inject
        return CountryDetailViewModel(useCase) as T
    }
}