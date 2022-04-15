package com.example.focusongraphql.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsets.Type
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.focusongraphql.databinding.ActivityMainBinding
import com.example.focusongraphql.network.models.Continent
import com.example.focusongraphql.utils.*
import com.example.focusongraphql.view.adapters.ContinentsAdapter
import com.example.focusongraphql.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeStatusBarTransparentAndContentFullScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            mainViewModel.getContinents().observe(this@MainActivity) {
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
                        showToast(it.message.toString())
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
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = ContinentsAdapter(continentList) { code, name ->
                    openListOfCountriesScreen(code, name)
                }
            }
        }
    }


    private fun openListOfCountriesScreen(code: String, name: String) {
        startActivity(
            Intent(this, CountriesActivity::class.java)
                .putExtra(SELECTED_CONTINENT, name).putExtra(SELECTED_CODE, code)
        )
    }

}