package com.san4illa.weather.ui.weather

import android.Manifest
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.san4illa.weather.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private val viewModel: WeatherViewModel by viewModels()

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val arePermissionsGranted = it[Manifest.permission.ACCESS_FINE_LOCATION] != false
                    && it[Manifest.permission.ACCESS_COARSE_LOCATION] != false
            viewModel.onPermissionsResult(arePermissionsGranted)
        }

    private val locationSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            viewModel.onSettingsResult(it.resultCode == RESULT_OK)
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

        viewModel.requestLocationSettings.observe(viewLifecycleOwner) { error ->
            val settingsRequest = IntentSenderRequest.Builder(error.resolution).build()
            locationSettingsLauncher.launch(settingsRequest)
        }
        viewModel.closeActivity.observe(viewLifecycleOwner) { if (it) closeActivity() }
    }

    private fun closeActivity() {
        requireActivity().finish()
    }
}