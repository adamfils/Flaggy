package com.techguy.flaggy.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techguy.flaggy.databinding.ActivityMainBinding
import com.techguy.flaggy.model.CountryData
import com.techguy.flaggy.utils.NetworkUtils.fetchCountries
import com.techguy.flaggy.utils.NetworkUtils.log
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    private var countryList = MutableLiveData<CountryData>()
    private var data = MutableLiveData<CountryData>()

    fun getCountryList(): LiveData<CountryData> {
        return countryList
    }

    fun fetchCountry() {
        viewModelScope.launch {
            data.value = fetchCountries()
            countryList.postValue(data.value)
        }
    }

    fun handleErrors(binding: ActivityMainBinding) {
        "handleErrors Error: ${data.value?.networkCode}".log()
        when (data.value?.networkCode) {
            0 -> {
                binding.errorText.visibility = View.VISIBLE
                val text = "No Internet Connection"
                binding.errorText.text = text
            }
            in 200..299 -> {
                binding.errorText.visibility = View.GONE
            }
            in 300..399 -> {
                //Between 300 and 399
                binding.errorText.visibility = View.VISIBLE
                val text = "Redirection Error"
                binding.errorText.text = text
            }
            in 400..499 -> {
                //Between 400 and 499
                binding.errorText.visibility = View.VISIBLE
                val text = "App Is Currently Having Issues, Please Try Again Later"
                binding.errorText.text = text
            }
            in 500..599 -> {
                //Between 500 and 599
                binding.errorText.visibility = View.VISIBLE
                val text = "Our Server Is Currently Having Issues, Please Try Again Later"
                binding.errorText.text = text
            }
        }
    }

}