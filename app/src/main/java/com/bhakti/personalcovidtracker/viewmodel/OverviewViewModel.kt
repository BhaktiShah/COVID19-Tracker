package com.bhakti.personalcovidtracker.viewmodel

import androidx.lifecycle.*
import com.bhakti.personalcovidtracker.data.JSONCountryDetail
import com.bhakti.personalcovidtracker.domain.FetchSummaryStatUseCase
import com.bhakti.personalcovidtracker.extensions.getTopCountries
import com.bhakti.personalcovidtracker.helper.NetworkHelper
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel to fetch & maintain global world COVID19 statistics
 * Fetch global stats & data for top 20 countries
 * Executes under viewModelScope
 *
 * @author Bhakti
 */
class OverviewViewModel constructor(private val fetchSummaryUseCase: FetchSummaryStatUseCase) : ViewModel() {

    private val globalStatLiveData by lazy { MediatorLiveData<GlobalStatsState>() }
    private val countryStatLiveData by lazy { MutableLiveData<CountryStatsState>() }

    fun getSummaryLiveData(): LiveData<GlobalStatsState> = globalStatLiveData
    fun getCountryStatLiveData(): LiveData<CountryStatsState> = countryStatLiveData

    fun fetchSummaryStats() {
        if (!NetworkHelper.isOnline())
            globalStatLiveData.value = GlobalStatsState.NoInternet
        else {
            globalStatLiveData.value = GlobalStatsState.Loading
            viewModelScope.launch {
                val res = fetchSummaryUseCase.execute()
                if (res == null) {
                    globalStatLiveData.value = GlobalStatsState.Error
                    countryStatLiveData.value = CountryStatsState.Error
                } else {
                    globalStatLiveData.value = GlobalStatsState.Success(res.Global.TotalConfirmed, res.Global.TotalRecovered, res.Global.TotalDeaths, res.Global.NewConfirmed, res.Date)
                    countryStatLiveData.value = CountryStatsState.Success(res.getTopCountries())
                }
            }
        }
    }

    sealed class CountryStatsState {
        object Error : CountryStatsState()
        data class Success(val data: List<JSONCountryDetail>) : CountryStatsState()
    }

    sealed class GlobalStatsState {
        object Loading : GlobalStatsState()
        object Error : GlobalStatsState()
        object NoInternet : GlobalStatsState()
        data class Success(val totalConfirm: Long, val totalRecovered: Long, val totalDeaths: Long, val newCases: Long, val lastUpdated: Date) : GlobalStatsState()
    }

    override fun onCleared() {
        super.onCleared()
    }
}
