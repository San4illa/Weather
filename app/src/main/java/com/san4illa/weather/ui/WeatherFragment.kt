package com.san4illa.weather.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.san4illa.weather.databinding.FragmentWeatherBinding
import com.san4illa.weather.repository.Repository

class WeatherFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            WeatherViewModelFactory(requireActivity().application, Repository())
        ).get(WeatherViewModel::class.java)
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val arePermissionsGranted = it[Manifest.permission.ACCESS_FINE_LOCATION] != false
                    && it[Manifest.permission.ACCESS_COARSE_LOCATION] != false

            if (arePermissionsGranted) {
                requestLocation()
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentWeatherBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.hourlyWeather.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = HourlyWeatherAdapter()
        }

        binding.dailyWeather.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DailyWeatherAdapter()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationPermissionLauncher.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    private fun requestLocation() {
        viewModel.location.observe(viewLifecycleOwner, { location ->
            viewModel.onLocationUpdated(location)
        })
    }
}