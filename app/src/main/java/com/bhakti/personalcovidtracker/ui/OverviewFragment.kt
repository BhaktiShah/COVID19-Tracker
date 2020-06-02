package com.bhakti.personalcovidtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.bhakti.personalcovidtracker.R
import com.bhakti.personalcovidtracker.data.JSONCountryDetail
import com.bhakti.personalcovidtracker.extensions.getDisplayString
import com.bhakti.personalcovidtracker.extensions.getFormattedString
import com.bhakti.personalcovidtracker.viewmodel.OverviewViewModel
import com.bhakti.personalcovidtracker.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.overview_fragment.*

/**
 * Summary view to show global stats and top countries listed
 *
 * @author Bhakti
 */
class OverviewFragment : Fragment(), CountryListAdapter.CountryEventListener {

    private val viewModel: OverviewViewModel by lazy { ViewModelProvider(activity!!, ViewModelFactory()).get(OverviewViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSummaryLiveData().observe(viewLifecycleOwner, Observer { onSummaryDataFetched(it) })
        viewModel.getCountryStatLiveData().observe(viewLifecycleOwner, Observer { onCountryDataFetched(it) })
    }

    private fun onSummaryDataFetched(state: OverviewViewModel.GlobalStatsState) {
        if (view!!.isAttachedToWindow) {
            hideProgress()
            when (state) {
                is OverviewViewModel.GlobalStatsState.Loading -> showProgress()
                is OverviewViewModel.GlobalStatsState.Error -> showError()
                is OverviewViewModel.GlobalStatsState.NoInternet -> showNoInternet()
                is OverviewViewModel.GlobalStatsState.Success -> showGlobalSummary(state)
            }
        }
    }

    private fun onCountryDataFetched(state: OverviewViewModel.CountryStatsState) {
        when (state) {
            is OverviewViewModel.CountryStatsState.Success -> showCountries(state.data)
        }
    }

    private fun showCountries(countries: List<JSONCountryDetail>) {
        listTitle.text = getString(R.string.top_countries)
        val adapter = CountryListAdapter(countries, this)
        recycler_view.post {
            recycler_view.adapter = adapter
            recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showError() {
        Toast.makeText(context, getString(R.string.msg_error), Toast.LENGTH_SHORT).show()
        listTitle.text = "No Data"
    }

    private fun showGlobalSummary(summary: OverviewViewModel.GlobalStatsState.Success) {
        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.summary_confirmed, summary.totalConfirm.getFormattedString()))
        stringBuilder.append("\n")
        stringBuilder.append(getString(R.string.summary_new, summary.newCases.getFormattedString()))
        stringBuilder.append("\n")
        stringBuilder.append(getString(R.string.summary_recovered, summary.totalRecovered.getFormattedString()))
        stringBuilder.append("\n")
        stringBuilder.append(getString(R.string.summary_deaths, summary.totalDeaths.getFormattedString()))

        statDesc.text = stringBuilder.toString()
        date.text = getString(R.string.date_updated, summary.lastUpdated.getDisplayString())
    }

    private fun showNoInternet() {
        Toast.makeText(context, getString(R.string.msg_no_internet), Toast.LENGTH_SHORT).show()
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onCountrySelected(country: JSONCountryDetail) {
        val action = OverviewFragmentDirections.actionOverviewFragmentToCountryDetailFragment(country.Slug)
        view?.findNavController()?.navigate(action)
    }
}
