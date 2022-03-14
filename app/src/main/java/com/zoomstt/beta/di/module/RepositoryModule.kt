package com.zoomstt.beta.di.module

import com.google.gson.Gson
import com.zoomstt.beta.data.local.AppPrefs
import com.zoomstt.beta.data.local.PrefHelper
import com.zoomstt.beta.data.repository.AppRepository
import com.zoomstt.beta.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository =
        appRepositoryImpl

    @Provides
    @Singleton
    fun providePrefsHelper(appPrefs: AppPrefs):PrefHelper = appPrefs
}
