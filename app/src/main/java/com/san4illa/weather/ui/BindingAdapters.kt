package com.san4illa.weather.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.san4illa.weather.domain.DailyWeather
import com.san4illa.weather.domain.Weather
import com.san4illa.weather.domain.State
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun ImageView.bindImage(url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}

@BindingAdapter("hourlyList")
fun RecyclerView.bindHourlyList(data: List<Weather>?) {
    (adapter as HourlyWeatherAdapter).submitList(data)
}

@BindingAdapter("time")
fun TextView.bindTime(time: Long) {
    val date = Date(time * 1000)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    text = formatter.format(date)
}

@BindingAdapter("dailyList")
fun RecyclerView.bindDailyList(data: List<DailyWeather>?) {
    (adapter as DailyWeatherAdapter).submitList(data)
}

@BindingAdapter("date")
fun TextView.bindDate(time: Long) {
    val date = Date(time * 1000)
    val formatter = SimpleDateFormat("d MMMM", Locale.getDefault())
    text = formatter.format(date)
}

@BindingAdapter("loaderState")
fun View.bindState(status: State) {
    visibility = when (status) {
        State.LOADING -> {
            View.VISIBLE
        }
        State.ERROR, State.DONE -> {
            View.GONE
        }
    }
}