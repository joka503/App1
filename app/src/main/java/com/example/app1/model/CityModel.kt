package com.example.app1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityModel(
    val id : Int,
    val name : String,
    val location : LocationModel
) : Parcelable
