package hr.foi.air.heatmapreport.view.data.api

import android.content.Context
import hr.foi.air.heatmapreport.view.interfaces.IAPIService

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:5039/"

    fun getApiService(context: Context): IAPIService {
        val client: OkHttpClient = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IAPIService::class.java)
    }
}