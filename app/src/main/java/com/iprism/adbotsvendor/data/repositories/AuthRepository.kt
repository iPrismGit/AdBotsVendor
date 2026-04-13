package com.iprism.adbotsvendor.data.repositories

import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
import com.iprism.adbotsvendor.data.remote.HealthDrinksService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun login(loginRequest: LoginRequest) = apiService.login(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) = apiService.register(registerRequest)
}