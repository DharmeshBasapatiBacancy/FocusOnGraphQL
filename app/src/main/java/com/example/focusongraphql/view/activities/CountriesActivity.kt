package com.example.focusongraphql.view.activities

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusongraphql.databinding.ActivityCountriesBinding
import com.example.focusongraphql.network.models.Country
import com.example.focusongraphql.utils.*
import com.example.focusongraphql.view.adapters.CountriesAdapter
import com.example.focusongraphql.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityCountriesBinding

    companion object {
        private const val TAG = "CountriesActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeStatusBarTransparentAndContentFullScreen()

        binding = ActivityCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            tvHeader.text = "Countries in ${intent.extras?.get(SELECTED_CONTINENT)}"
            imgBackToContinents.setOnClickListener {
                onBackPressed()
            }
        }

        getListOfCountries(intent.extras?.get(SELECTED_CODE) as String)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getListOfCountries(selectedContinentCode: String) {
        lifecycleScope.launch {

            mainViewModel.getCountriesOfSelectedContinent(selectedContinentCode)
                .observe(this@CountriesActivity) {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.show()
                            binding.rvCountries.gone()
                        }
                        is Resource.Success -> {
                            binding.progressBar.gone()
                            binding.rvCountries.show()
                            setupUI(convertToCountryDataModel(it.data))
                        }
                        is Resource.Error -> {
                            binding.progressBar.gone()
                            binding.rvCountries.show()
                        }
                        else -> {
                            Log.d(TAG, "setupObserver: Else - Called")
                        }
                    }
                }

        }
    }

    private fun setupUI(data: List<Country>) {
        binding.apply {
            rvCountries.apply {
                layoutManager = LinearLayoutManager(this@CountriesActivity)
                adapter = CountriesAdapter(data)
            }
        }
    }
}