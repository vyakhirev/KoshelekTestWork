package ru.vyakhirev.koshelektestwork.di.api

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import javax.inject.Singleton

@Module
class ApiModule {
    private val BASE_URL = "https://api.binance.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(loggingInterceptor)
        .build()

    var retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(): ApiBinance {
        return retrofit.create(ApiBinance::class.java)
    }
}