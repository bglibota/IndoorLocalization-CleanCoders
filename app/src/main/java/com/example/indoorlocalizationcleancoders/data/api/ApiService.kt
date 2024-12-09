package com.example.indoorlocalizationcleancoders.data.api

import com.example.indoorlocalizationcleancoders.data.models.LoginRequest
import com.example.indoorlocalizationcleancoders.data.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService{
    @POST("Login")
    suspend fun login(@Body request: LoginRequest) : Response<Void>

    @POST("Register")
    suspend fun register(@Body request: RegisterRequest) : Response<Void>
}