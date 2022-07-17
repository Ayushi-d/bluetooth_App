package com.example.f1bleapp.DI

import android.app.Application
import androidx.room.Room
import com.example.f1bleapp.Data.Local.DeviceDao
import com.example.f1bleapp.Data.Local.DeviceDetailDatabase
import com.example.f1bleapp.Repository.DefaultDeviceRepository
import com.example.f1bleapp.Repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDeviceDetailDataBase(
        app: Application
    ) = Room.databaseBuilder(app, DeviceDetailDatabase::class.java, "device_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesDeviceDao(
        database: DeviceDetailDatabase
    ) = database.deviceDao()

    @Singleton
    @Provides
    fun providesRepository(
        dao: DeviceDao
    ) = DefaultDeviceRepository(dao) as DeviceRepository
}