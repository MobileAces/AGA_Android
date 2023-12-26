package com.aga.data.data.api

import com.aga.data.data.model.user.UserRequest
import com.aga.data.data.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("users/sign-in")
    suspend fun login(
        @Body user: UserRequest
    ): UserResponse

    @GET("users/id-duplicate")
    suspend fun isDuplicatedId(
        @Query("userId") id: String
    ): Boolean

    @GET("users/phone-duplicate")
    suspend fun isDuplicatedPhone(
        @Query("phoneNumber") phone: String
    ): Boolean

    @GET("users/nickname-duplicate")
    suspend fun isDuplicatedNickname(
        @Query("userNickname") nickname: String
    ): Boolean

}