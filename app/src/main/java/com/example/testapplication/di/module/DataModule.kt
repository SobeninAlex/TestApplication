package com.example.testapplication.di.module

import com.example.testapplication.data.network.api.ApiService
import com.example.testapplication.data.repository.DetailQuoteRepositoryImpl
import com.example.testapplication.data.repository.ListQuoteRepositoryImpl
import com.example.testapplication.di.annotation.ApplicationScope
import com.example.testapplication.domain.repository.DetailQuoteRepository
import com.example.testapplication.domain.repository.ListQuoteRepository
import com.example.testapplication.utill.BASE_URL
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
    fun bindDetailQuoteRepository(impl: DetailQuoteRepositoryImpl): DetailQuoteRepository

    @[ApplicationScope Binds]
    fun bindListQuoteRepository(impl: ListQuoteRepositoryImpl): ListQuoteRepository

    companion object {

        @Provides
        fun provideBaseUrl() = BASE_URL

        @Provides
        fun provideOkHttpClient() =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        @[ApplicationScope Provides]
        fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String) =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        @[ApplicationScope Provides]
        fun provideApiService(retrofit: Retrofit) = retrofit.create<ApiService>()

    }

}