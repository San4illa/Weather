package com.san4illa.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.san4illa.weather.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    companion object {
        private const val REQUEST_PERMISSION_CODE = 101
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            WeatherViewModelFactory(requireActivity().application)
        ).get(WeatherViewModel::class.java)
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

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSION_CODE
            )
        } else {
            requestLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // TODO show error message
            }
        }
    }

    private fun requestLocation() {
        viewModel.location.observe(viewLifecycleOwner, { location ->
            viewModel.onLocationUpdated(location)
        })
    }
}