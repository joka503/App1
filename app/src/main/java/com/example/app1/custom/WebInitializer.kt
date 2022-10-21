package com.example.app1.custom

import com.example.app1.constants.Constants
import com.example.app1.data.IWeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebInitializer {

    private val defaultApi = Retrofit.Builder()
        .baseUrl(Constants.Api)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val locationApi = Retrofit.Builder()
        .baseUrl(Constants.ApiLocation)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun weatherService() : IWeatherService{
        return defaultApi.create(IWeatherService::class.java)
    }

    fun locationService():IWeatherService{
        return locationApi.create(IWeatherService::class.java)
    }

}