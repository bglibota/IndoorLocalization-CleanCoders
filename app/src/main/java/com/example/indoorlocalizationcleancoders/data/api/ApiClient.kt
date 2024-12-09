package com.example.indoorlocalizationcleancoders.data.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://10.0.2.2:7197/api/User/"

    fun getApiService(context: Context): ApiService {
        val client: OkHttpClient = SafeOkHttpClient.getSafeOkHttpClient(context)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}