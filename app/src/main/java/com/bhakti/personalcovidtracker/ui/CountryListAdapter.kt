package com.bhakti.personalcovidtracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhakti.personalcovidtracker.R
import com.bhakti.personalcovidtracker.data.JSONCountryDetail
import com.bhakti.personalcovidtracker.extensions.getFormattedString

/**
 * Recycler view adapter for country list
 *
 * @author Bhakti
 */
class CountryListAdapter(private val countryList: List<JSONCountryDetail>, val eventListener: CountryEventListener? = null) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
        return ViewHolder(view)
    }

    fun getItem(position: Int): JSONCountryDetail {
        return countryList[position]
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = getItem(position)
        holder.txtName.text = country.Country
        holder.txtCount.text = country.TotalConfirmed.getFormattedString()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtName = view.findViewById<TextView>(R.id.tvName)
        var txtCount = view.findViewById<TextView>(R.id.tvCount)
        private var container = view.findViewById<View>(R.id.item_container)

        init {
            container.setOnClickListener {
                eventListener?.onCountrySelected(getItem(adapterPosition))
            }
        }
    }

    interface CountryEventListener {
        fun onCountrySelected(country: JSONCountryDetail)
    }
}