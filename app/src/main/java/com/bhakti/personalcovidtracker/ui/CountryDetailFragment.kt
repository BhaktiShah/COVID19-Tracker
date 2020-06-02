package com.bhakti.personalcovidtracker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bhakti.personalcovidtracker.Constants
import com.bhakti.personalcovidtracker.R
import com.bhakti.personalcovidtracker.data.JSONCountryDetail
import com.bhakti.personalcovidtracker.extensions.getFormattedString
import com.bhakti.personalcovidtracker.viewmodel.OverviewViewModel
import com.bhakti.personalcovidtracker.viewmodel.ViewModelFactory
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.country_detail_fragment.*

/**
 * Country detail view
 *
 * @author Bhakti
 */
class CountryDetailFragment : Fragment() {

    private lateinit var viewModel: OverviewViewModel
    private val args: CountryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.country_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, ViewModelFactory()).get(OverviewViewModel::class.java)
        viewModel.getCountryStatLiveData().observe(viewLifecycleOwner, Observer { onDataFetched(it) })
        //viewModel.fetchLiveCountryStats(args.countrySlug)
    }

    private fun onDataFetched(state: OverviewViewModel.CountryStatsState) {
        if (view!!.isAttachedToWindow) {
            when (state) {
                is OverviewViewModel.CountryStatsState.Error -> showError()
                is OverviewViewModel.CountryStatsState.Success -> showCountryInfo(args.countrySlug, state.data)
            }
        }
    }

    private fun showCountryInfo(countrySlug: String, infoList: List<JSONCountryDetail>) {
        val countryInfo = infoList.firstOrNull { it.Slug == countrySlug }
        if (countryInfo == null) {
            showError()
            return
        }
        country_title.text = getString(R.string.country_detail_title, countryInfo.Country)

        //create the data set
        createDataSet(countryInfo)
    }

    private fun showError() {
        Toast.makeText(context, getString(R.string.msg_error), Toast.LENGTH_SHORT).show()
    }

    private fun createDataSet(countryInfo: JSONCountryDetail) {
        val pieEntries = getPieEntries(countryInfo)
        val legendEntries = getLegendEntries(pieEntries)
        val pieDataSet = PieDataSet(pieEntries, getString(R.string.label_covid))
        pieDataSet.setDrawValues(true)
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextSize = 10f
        pieDataSet.colors = getPieColors()
        addDataSet(pieDataSet, legendEntries)
    }

    private fun addDataSet(pieDataSet: PieDataSet, legendEntries: List<LegendEntry>) {
        val pieData = PieData(pieDataSet)
        country_stat_piechart.data = pieData
        country_stat_piechart.setDrawCenterText(true)
        country_stat_piechart.centerText = getString(R.string.overview)
        country_stat_piechart.description.isEnabled = false
        country_stat_piechart.setDrawEntryLabels(false)
        country_stat_piechart.transparentCircleRadius = 0f
        country_stat_piechart.setTouchEnabled(true)
        setLegend(legendEntries)
        country_stat_piechart.animateY(Constants.ANIMATION_TIMER)
        country_stat_piechart.invalidate()
    }

    private fun setLegend(legendEntries: List<LegendEntry>) {
        country_stat_piechart.legend.isEnabled = true
        //country_stat_piechart.legend.orientation = Legend.LegendOrientation.VERTICAL
        country_stat_piechart.legend.isWordWrapEnabled = true
        country_stat_piechart.legend.setCustom(legendEntries)
    }

    private fun getPieEntries(countryInfo: JSONCountryDetail): List<PieEntry> {
        //create the data set
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(countryInfo.TotalConfirmed.toFloat(), getString(R.string.total_confirmed)))
        pieEntries.add(PieEntry(countryInfo.NewConfirmed.toFloat(), getString(R.string.new_confirmed)))
        pieEntries.add(PieEntry(countryInfo.TotalRecovered.toFloat(), getString(R.string.total_recovered)))
        pieEntries.add(PieEntry(countryInfo.NewRecovered.toFloat(), getString(R.string.new_recovered)))
        pieEntries.add(PieEntry(countryInfo.TotalDeaths.toFloat(), getString(R.string.total_deaths)))
        pieEntries.add(PieEntry(countryInfo.NewDeaths.toFloat(), getString(R.string.new_deaths)))
        return pieEntries
    }

    private fun getPieColors(): List<Int> {
        return listOf(ContextCompat.getColor(context!!, R.color.red), Color.BLUE, ContextCompat.getColor(context!!, R.color.green), Color.CYAN, Color.DKGRAY, Color.YELLOW)
    }

    private fun getLegendEntries(pieEntries: List<PieEntry>): List<LegendEntry> {
        var legend: LegendEntry
        val colors = getPieColors()
        val legendEntries = ArrayList<LegendEntry>(pieEntries.size)
        pieEntries.forEachIndexed { index, pieEntry ->
            legend = LegendEntry()
            legend.label = "${pieEntry.label} : ${pieEntry.value.getFormattedString()}"
            legend.formColor = colors[index]
            legendEntries.add(legend)
        }
        return legendEntries
    }
}
