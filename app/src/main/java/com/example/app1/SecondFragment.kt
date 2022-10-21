package com.example.app1

import android.os.Bundle
import android.os.Debug
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.app1.constants.Constants
import com.example.app1.custom.WebInitializer
import com.example.app1.data.ChosenCityViewModel
import com.example.app1.databinding.FragmentSecondBinding
import com.example.app1.model.CityModel
import com.example.app1.model.LocationInfoModel
import com.example.app1.model.LocationModel
import com.example.app1.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var selectedCity: CityModel
    private lateinit var viewModel: ChosenCityViewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments?.getParcelable<CityModel>("city")
        if(bundle != null)
        {
            selectedCity = bundle
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (selectedCity != null){
            val call = WebInitializer().weatherService().getInfoByCountry(Constants.ApiKey,selectedCity.location.lat,selectedCity.location.lon,"metric")
            call.enqueue(object: Callback<WeatherModel?> {
                override fun onResponse(call: Call<WeatherModel?>?,
                                        response: Response<WeatherModel?>?) {
                    response?.body()?.let {
                        val response: WeatherModel = it

                        if (selectedCity.name == "Current Location"){
                            val locationCall = WebInitializer().locationService().getLocationName(Constants.ApiKey,selectedCity.location.lat, selectedCity.location.lon, 1)
                            locationCall.enqueue(object: Callback<Array<LocationInfoModel>> {
                                override fun onResponse(call: Call<Array<LocationInfoModel>>,
                                                        response: Response<Array<LocationInfoModel>>?) {
                                    response?.body()?.let {
                                        val detailInfo : Array<LocationInfoModel> = it
                                        binding.textViewTitle.text = detailInfo[0].name
                                    }
                                }

                                override fun onFailure(call: Call<Array<LocationInfoModel>>?,
                                                       t: Throwable?) {
                                    Toast.makeText(activity!!, "Error obtaining location name from server!", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                        else
                        {
                            binding.textViewTitle.text = selectedCity.name
                        }
                        binding.textViewCurrentTemp.text = response.main?.feelsLike.toString() + "º"
                        binding.textViewMaxTemp.text = response.main?.tempMax.toString() + "º"
                        binding.textViewMinTemp.text = response.main?.tempMin.toString() + "º"
                        binding.textViewHumidity.text = response.main?.humidity.toString() + "%"
                        binding.textViewWindSpeed.text = response.wind?.speed.toString() + " m/s"
                        binding.textViewDescription.text = response.weather[0].description

                        Glide.with(activity!!)
                            .load(Constants.IconUrl + response.weather[0].icon+"@2x.png")
                            .into(binding.imageviewIconWeather)
                    }
                }

                override fun onFailure(call: Call<WeatherModel?>?,
                                       t: Throwable?) {
                    Toast.makeText(activity!!, "Error obtaining data from server!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
