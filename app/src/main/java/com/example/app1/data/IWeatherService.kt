package com.example.app1.data

import com.example.app1.model.LocationInfoModel
import com.example.app1.model.LocationModel
import com.example.app1.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {
     @GET("weather")
     fun getInfoByCountry(@Query("appid") key: String,
                          @Query("lat") latitude: Double,
                          @Query("lon") longitude: Double,
                          @Query("units") units: String): Call<WeatherModel>

    @GET("reverse")
    fun getLocationName(@Query("appid") key: String,
                         @Query("lat") latitude: Double,
                         @Query("lon") longitude: Double,
                         @Query("limit") limit: Int): Call<Array<LocationInfoModel>>
}