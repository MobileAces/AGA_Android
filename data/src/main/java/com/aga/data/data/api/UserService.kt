package com.aga.data.data.api

import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.user.join.JoinRequest
import com.aga.data.data.model.user.join.JoinResponse
import com.aga.data.data.model.user.login.LoginRequest
import com.aga.data.data.model.user.login.LoginResponse
import com.aga.data.data.model.user.passwordchange.PasswordChangeRequest
import com.aga.data.data.model.user.passwordchange.PasswordChangeResponse
import com.aga.data.data.model.user.UserResponse
import com.aga.data.data.model.user.duplicated.DuplicatedResponse
import com.aga.data.data.model.user.update.UserUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @POST("users/sign-in")
    suspend fun login(
        @Body user: LoginRequest
    ): Response<DefaultResponse<LoginResponse>>

    @GET("users/id-duplicate")
    suspend fun isDuplicatedId(
        @Query("userId") id: String
    ): Response<DefaultResponse<DuplicatedResponse>>

    @GET("users/phone-duplicate")
    suspend fun isDuplicatedPhone(
        @Query("phoneNumber") phone: String
    ): Response<DefaultResponse<DuplicatedResponse>>

    @GET("users/nickname-duplicate")
    suspend fun isDuplicatedNickname(
        @Query("userNickname") nickname: String
    ): Response<DefaultResponse<DuplicatedResponse>>

    @POST("users/sign-up")
    suspend fun join(
        @Body user: JoinRequest
    ): Response<DefaultResponse<JoinResponse>>

    @GET("users/{userId}")
    suspend fun getUserInfo(
        @Path(value = "userId") id: String
    ): Response<DefaultResponse<UserResponse>>

    @DELETE("users/{userId}")
    suspend fun deleteUser(
        @Path(value = "userId") id: String
    ): String

    @PUT("users")
    suspend fun updateUser(
        @Body user: UserUpdateRequest
    ): Response<DefaultResponse<UserResponse>>

    @POST("users/password")
    suspend fun updatePassword(
        @Body pwChangeRequest: PasswordChangeRequest
    ): PasswordChangeResponse
}