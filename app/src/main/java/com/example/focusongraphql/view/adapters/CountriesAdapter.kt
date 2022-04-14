package com.example.focusongraphql.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.focusongraphql.databinding.RowItemCountriesBinding
import com.example.focusongraphql.network.models.Country
import com.example.focusongraphql.utils.setRandomBackgroundColor

class CountriesAdapter(private val countryList: List<Country>) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    inner class ViewHolder(val rowItemCountriesBinding: RowItemCountriesBinding) :
        RecyclerView.ViewHolder(rowItemCountriesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemCountriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            rowItemCountriesBinding.apply {
                country = countryList[position]
                lnrCountry.setRandomBackgroundColor()
            }
        }
    }

    override fun getItemCount() = countryList.size

}
