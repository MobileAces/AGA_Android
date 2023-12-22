package com.aga.data.data.api

import com.aga.data.data.model.user.UserRequest
import com.aga.data.data.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("users/sign-in")
    suspend fun login(
        @Body user: UserRequest
    ): UserResponse
}