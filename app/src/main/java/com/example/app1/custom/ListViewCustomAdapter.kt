package com.example.app1.custom

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.app1.R
import com.example.app1.model.CityModel
import com.example.app1.model.WeatherModel

class ListViewCustomAdapter(private val context: Activity, private val cities: Array<CityModel>) : ArrayAdapter<CityModel>(context, R.layout.row_item, cities) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.row_item, null, true)

        val cityNameText = rowView.findViewById(R.id.textviewCity) as TextView
        val imageView = rowView.findViewById(R.id.imageviewWeather) as ImageView
        val statusText = rowView.findViewById(R.id.textviewWeatherStatus) as TextView
        val tempText = rowView.findViewById(R.id.textviewTemp) as TextView

        cityNameText.text = cities[position].name
        statusText.text = cities[position].name
        tempText.text = cities[position].name

        /*
        Glide.with(context)
            .load(cities[position].name)
            .fitCenter()
            .circleCrop()
            .into(imageView)
         */

        return rowView
    }
}