package com.techguy.flaggy.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.techguy.flaggy.adapter.CountryAdapter
import com.techguy.flaggy.databinding.ActivityMainBinding
import com.techguy.flaggy.model.CountryModel
import com.techguy.flaggy.viewmodel.CountryViewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var viewModel = lazy { CountryViewModel() }.value
    private var adapter = CountryAdapter()
    private var countryList = ArrayList<CountryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.countryList.layoutManager = LinearLayoutManager(this)
        binding.countryList.adapter = adapter

        viewModel.fetchCountry()
        viewModel.getCountryList().observe(this) {
            countryList = it.countries
            viewModel.handleErrors(binding)
            adapter.setList(countryList)
        }
    }

    override fun onResume() {
        super.onResume()
        if (countryList.isEmpty()) {
            viewModel.fetchCountry()
        }
    }
}