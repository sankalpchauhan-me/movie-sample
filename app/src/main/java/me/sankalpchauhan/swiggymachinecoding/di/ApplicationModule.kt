package me.sankalpchauhan.swiggymachinecoding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://www.omdbapi.com/"

}