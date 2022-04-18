package com.example.focusongraphql.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusongraphql.databinding.FragmentCountriesBinding
import com.example.focusongraphql.network.models.Country
import com.example.focusongraphql.utils.Resource
import com.example.focusongraphql.utils.convertToCountryDataModel
import com.example.focusongraphql.utils.gone
import com.example.focusongraphql.utils.show
import com.example.focusongraphql.view.adapters.CountriesAdapter
import com.example.focusongraphql.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentCountriesBinding
    private val args: CountriesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesBinding.inflate(layoutInflater)
        binding.apply {

            tvHeader.text = "Countries in ${args.name}"
            imgBackToContinents.setOnClickListener {
                //onBackPressed()
                findNavController().navigateUp()
            }
        }
        getListOfCountries(args.code)
        return binding.root
    }

    private fun getListOfCountries(selectedContinentCode: String) {
        lifecycleScope.launch {

            mainViewModel.getCountriesOfSelectedContinent(selectedContinentCode)
                .observe(requireActivity()) {
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
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = CountriesAdapter(data)
            }
        }
    }

    companion object {
        private const val TAG = "CountriesFragment"
    }
}