package com.san4illa.weather.di

import android.content.Context
import com.san4illa.weather.BuildConfig
import com.san4illa.weather.data.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.darksky.net/forecast/${BuildConfig.API_KEY}/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideGmsLocationProvider(
        @ApplicationContext context: Context
    ): com.google.android.gms.location.FusedLocationProviderClient {
        return com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideHmsLocationProvider(
        @ApplicationContext context: Context
    ): com.huawei.hms.location.FusedLocationProviderClient {
        return com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context)
    }
}