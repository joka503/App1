package com.example.app1.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app1.model.CityModel

class ChosenCityViewModel : ViewModel() {
    val data = MutableLiveData<CityModel>()

    fun city(cityModel: CityModel){
        data.value = cityModel
    }
}