package com.example.testapplication.di.module

import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.data.repository.DetailQuoteRepositoryImpl
import com.example.testapplication.data.repository.ListQuoteRepositoryImpl
import com.example.testapplication.di.annotation.ApplicationScope
import com.example.testapplication.domain.repository.DetailQuoteRepository
import com.example.testapplication.domain.repository.ListQuoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindDetailQuoteRepository(impl: com.example.testapplication.data.repository.DetailQuoteRepositoryImpl): com.example.testapplication.domain.repository.DetailQuoteRepository

    @[ApplicationScope Binds]
    fun bindListQuoteRepository(impl: com.example.testapplication.data.repository.ListQuoteRepositoryImpl): com.example.testapplication.domain.repository.ListQuoteRepository

    companion object {

        private const val BASE_URL = "https://oico.app/"

        @Provides
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        @[ApplicationScope Provides]
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        @Provides
        fun provideApiService(retrofit: Retrofit) =
            retrofit.create<com.example.testapplication.data.network.api.ApiService>()

    }

}