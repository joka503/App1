package com.example.app1

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.app1.custom.ListViewCustomAdapter
import com.example.app1.data.ChosenCityViewModel
import com.example.app1.databinding.FragmentFirstBinding
import com.example.app1.model.CityModel
import com.example.app1.model.LocationModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.JsonSerializer


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var viewModel: ChosenCityViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var cities = arrayOf(
        CityModel(1,"Lisbon", LocationModel(38.736946,-9.142685)),
        CityModel(2,"Madrid", LocationModel(40.416775,-3.703790)),
        CityModel(3,"Paris", LocationModel(48.858093,2.294694)),
        CityModel(4,"Berlin", LocationModel(52.520007,13.404954)),
        CityModel(5,"Copenhagen", LocationModel(55.676098,12.568337)),
        CityModel(6,"Rome", LocationModel(41.890251,12.492373)),
        CityModel(7,"London", LocationModel(51.507351,-0.127758)),
        CityModel(8,"Dublin", LocationModel(53.350140,-6.266155)),
        CityModel(9,"Prague", LocationModel(50.073658,14.418540)),
        CityModel(10,"Vienna", LocationModel(48.210033,16.363449)),
    )
    private var locationAdded : Boolean = false

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        val adapter = ListViewCustomAdapter(activity!!,cities)
        binding.listviewCities.adapter = adapter

        binding.listviewCities.onItemClickListener =
            OnItemClickListener { adapterView, view, position, l -> // TODO Auto-generated method stub
                val value: CityModel? = cities[position]
                if (value != null) {
                    Toast.makeText(activity, value.name, Toast.LENGTH_SHORT).show()

                    val bundle = Bundle()
                    bundle.putParcelable("city", value)
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
                }
            }
    }

    override fun onResume() {
        super.onResume()

        if (!locationAdded) {
            when {
                activity?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED -> {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            if (location != null)
                                cities += CityModel(
                                    11,
                                    "Current Location",
                                    LocationModel(location.latitude, location.longitude)
                                )
                            cities.sortByDescending { t -> t.id }
                            val adapter = ListViewCustomAdapter(activity!!, cities)
                            binding.listviewCities.adapter = adapter
                            locationAdded = true
                        }
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    showInContextUI()
                }
                else -> {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ), 1
                    )
                }
            }
        }
    }

    private fun showInContextUI(){
        val dialogBuilder = activity?.let {
            AlertDialog.Builder(it).also {

                it.setMessage("The app needs access to the device location to be able to show the weather information!")

                    .setPositiveButton("Accept", DialogInterface.OnClickListener(){ dialog, _ -> requestPermissions(
                        arrayOf( Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
                    })

                    .setNegativeButton("Deny", DialogInterface.OnClickListener(){ dialog,_ -> dialog.cancel()
                    })
            }
        }
        val alert = dialogBuilder?.create()
        alert?.setTitle("Alert")
        alert?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
