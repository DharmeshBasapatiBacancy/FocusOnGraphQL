package com.example.focusongraphql.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.focusongraphql.databinding.RowItemContinentsBinding
import com.example.focusongraphql.network.models.Continent
import com.example.focusongraphql.utils.setRandomBackgroundColor

class ContinentsAdapter(
    private val continentList: List<Continent>,
    private val onContinentClicked: (String, String) -> Unit
) : RecyclerView.Adapter<ContinentsAdapter.ViewHolder>() {

    inner class ViewHolder(val rowItemContinentsBinding: RowItemContinentsBinding) :
        RecyclerView.ViewHolder(rowItemContinentsBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemContinentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(continentList[position]) {
                rowItemContinentsBinding.tvContinentName.text = name
                rowItemContinentsBinding.root.setOnClickListener {
                    onContinentClicked(code, name)
                }
                rowItemContinentsBinding.lnrContinent.setRandomBackgroundColor()
            }
        }
    }

    override fun getItemCount() = continentList.size
}