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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.focusongraphql.databinding.FragmentContinentsBinding
import com.example.focusongraphql.network.models.Continent
import com.example.focusongraphql.utils.*
import com.example.focusongraphql.view.adapters.ContinentsAdapter
import com.example.focusongraphql.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContinentsFragment : Fragment() {

    companion object {
        private const val TAG = "ContinentsFragment"
    }

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentContinentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContinentsBinding.inflate(layoutInflater)
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            mainViewModel.getContinents().observe(requireActivity()) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.show()
                        binding.rvContinents.gone()
                    }
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        binding.rvContinents.show()
                        setupUI(convertToContinentDataModel(it.data?.toList()))
                    }
                    is Resource.Error -> {
                        binding.progressBar.gone()
                        binding.rvContinents.show()
                        requireActivity().showToast(it.message.toString())
                    }
                    else -> {
                        Log.d(TAG, "setupObserver: Else - Called")
                    }
                }
            }

        }
    }

    private fun setupUI(continentList: List<Continent>) {
        binding.apply {
            rvContinents.apply {
                layoutManager = GridLayoutManager(requireActivity(), 2)
                adapter = ContinentsAdapter(continentList) { code, name ->
                    openListOfCountriesScreen(code, name)
                }
            }
        }
    }


    private fun openListOfCountriesScreen(code: String, name: String) {
        findNavController().navigate(
            ContinentsFragmentDirections.actionContinentsFragmentToCountriesFragment(
                code,
                name
            )
        )
    }
}