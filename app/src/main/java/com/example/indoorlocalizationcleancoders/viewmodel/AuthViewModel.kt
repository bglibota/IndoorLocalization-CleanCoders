package com.example.indoorlocalizationcleancoders.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indoorlocalizationcleancoders.data.api.ApiClient
import com.example.indoorlocalizationcleancoders.data.models.LoginResponse
import com.example.indoorlocalizationcleancoders.data.models.LoginRequest
import com.example.indoorlocalizationcleancoders.data.models.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel : ViewModel() {
    var loginStatus = mutableStateOf("")
    var registrationStatus = mutableStateOf("")


    fun login(context: Context, username: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(username, password)
            val response: Response<LoginResponse> = ApiClient.getApiService(context).login(request)

            if (response.isSuccessful) {
                loginStatus.value = "Login successful"
                val user = response.body()
                if (user != null) {
                    val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("userId", user.id)
                    editor.putString("userName", user.name)
                    editor.putString("userUsername", user.username)
                    editor.putInt("userRoleId", user.roleId)
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                    Log.d("LoginResponse", "User: ${user.username}, Name: ${user.name}, Role: ${user.roleId}")
                }
            } else {
                loginStatus.value = "Login failed"
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