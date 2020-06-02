package com.bhakti.personalcovidtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhakti.personalcovidtracker.data.JSONCountry
import com.bhakti.personalcovidtracker.domain.FetchCountryStatUseCase
import com.bhakti.personalcovidtracker.helper.NetworkHelper
import kotlinx.coroutines.launch

/**
 * ViewModel to fetch & maintain live country status
 * Executes coroutine under viewModelScope
 *
 * @author Bhakti
 */
class CountryDetailViewModel constructor(private val fetchLiveCountryStatUseCase: FetchCountryStatUseCase) : ViewModel() {

    private val countryLiveData by lazy { MutableLiveData<CountryStatsState>() }

    fun getCountryLiveData(): LiveData<CountryStatsState> = countryLiveData

    fun fetchLiveCountryStats(countrySlug: String) {
        if (!NetworkHelper.isOnline())
            countryLiveData.value = CountryStatsState.NoInternet
        else {
            countryLiveData.value = CountryStatsState.Loading
            viewModelScope.launch {
                val result = fetchLiveCountryStatUseCase.execute(FetchCountryStatUseCase.Params(countrySlug))
                countryLiveData.value = if (result == null) CountryStatsState.Error else CountryStatsState.Success(result)
            }
        }
    }

    sealed class CountryStatsState {
        object Loading : CountryStatsState()
        object Error : CountryStatsState()
        object NoInternet : CountryStatsState()
        data class Success(val data: JSONCountry) : CountryStatsState()
    }

    override fun onCleared() {
        super.onCleared()
    }
}
