package me.sankalpchauhan.swiggymachinecoding.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import me.sankalpchauhan.swiggymachinecoding.data.api.NetworkService
import me.sankalpchauhan.swiggymachinecoding.di.BaseUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideJson(): Json{
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    fun provideOkHttp(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit{
        val mediaType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(json.asConverterFactory(mediaType)).build()
    }

    @Provides
    fun provideNetworkService(retrofit: Retrofit): NetworkService{
        return retrofit.create(NetworkService::class.java)
    }
}