package com.san4illa.weather.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san4illa.weather.databinding.HourlyWeatherItemBinding
import com.san4illa.weather.domain.model.Weather
import com.san4illa.weather.ui.weather.HourlyWeatherAdapter.HourlyWeatherViewHolder

class HourlyWeatherAdapter : ListAdapter<Weather, HourlyWeatherViewHolder>(HourlyWeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val binding = HourlyWeatherItemBinding.inflate(LayoutInflater.from(parent.context))
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HourlyWeatherViewHolder(
        private val binding: HourlyWeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            binding.weather = weather
            binding.executePendingBindings()
        }
    }
}

class HourlyWeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean = oldItem.time == newItem.time

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean = oldItem == newItem
}
