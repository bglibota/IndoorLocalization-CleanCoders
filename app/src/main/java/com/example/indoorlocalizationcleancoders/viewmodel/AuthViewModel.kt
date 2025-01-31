package com.example.indoorlocalizationcleancoders.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indoorlocalizationcleancoders.data.api.ApiClient
import com.example.indoorlocalizationcleancoders.data.models.LoginRequest
import com.example.indoorlocalizationcleancoders.data.models.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class AuthViewModel : ViewModel() {
    var loginStatus = mutableStateOf("")
    var registrationStatus = mutableStateOf("")


    fun login(context: Context, username: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(username, password)

            try {
                val response: Response<Void> = ApiClient.getApiService(context).login(request)

                if (response.isSuccessful) {
                    loginStatus.value = "Login successful"
                } else {
                    loginStatus.value = "Login failed"
                }
            } catch (e: IOException) {
                loginStatus.value = "Database connection failed. Please try again later."
            } catch (e: Exception) {
                loginStatus.value = "An unexpected error occurred. Please try again."
            }
        }
    }

    fun register(context: Context, name: String, username: String, password: String) {
        viewModelScope.launch {
            val request = RegisterRequest(name, username, password)
            val response: Response<Void> = ApiClient.getApiService(context).register(request)

            if (response.isSuccessful) {
                registrationStatus.value = "Registration successful"
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Registration failed"
                registrationStatus.value = errorMessage
            }
        }
    }
}