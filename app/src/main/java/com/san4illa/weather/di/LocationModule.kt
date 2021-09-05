package com.san4illa.weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Singleton
    @Provides
    fun provideGmsLocationProvider(
        @ApplicationContext context: Context
    ): com.google.android.gms.location.FusedLocationProviderClient {
        return com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideGmsSettingsClient(
        @ApplicationContext context: Context
    ): com.google.android.gms.location.SettingsClient {
        return com.google.android.gms.location.LocationServices.getSettingsClient(context)
    }

    @Singleton
    @Provides
    fun provideHmsLocationProvider(
        @ApplicationContext context: Context
    ): com.huawei.hms.location.FusedLocationProviderClient {
        return com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideHmsSettingsClient(
        @ApplicationContext context: Context
    ): com.huawei.hms.location.SettingsClient {
        return com.huawei.hms.location.LocationServices.getSettingsClient(context)
    }
}