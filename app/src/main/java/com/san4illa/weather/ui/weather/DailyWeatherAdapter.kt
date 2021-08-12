package com.san4illa.weather.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san4illa.weather.databinding.DailyWeatherItemBinding
import com.san4illa.weather.domain.model.DailyWeather

class DailyWeatherAdapter : ListAdapter<DailyWeather, DailyWeatherAdapter.DailyWeatherViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val binding = DailyWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DailyWeatherViewHolder(private val binding: DailyWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: DailyWeather) {
            binding.weather = weather
            binding.executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
            return oldItem.time == newItem.time
        }
    }
}