package com.aga.data.data.repository.user.remote

import com.aga.data.data.model.user.PasswordChangeRequest
import com.aga.data.data.model.user.PasswordChangeResponse
import com.aga.domain.model.User

interface UserRemoteDataSource {
    suspend fun login(user: User): String

    suspend fun isDuplicatedId(id: String): Boolean

    suspend fun isDuplicatedPhone(phone: String): Boolean

    suspend fun isDuplicatedNickname(nickname: String): Boolean

    suspend fun join(user: User): Boolean

    suspend fun getUserInfo(id: String): User

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(user: User): User

    suspend fun updatePassword(id: String, prePw: String, newPw: String): Boolean
}