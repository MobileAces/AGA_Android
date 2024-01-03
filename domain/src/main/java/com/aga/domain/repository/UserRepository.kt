package com.aga.domain.repository

import com.aga.domain.model.User

interface UserRepository {
    suspend fun login(user: User): String

    suspend fun isDuplicatedId(id: String): Boolean

    suspend fun isDuplicatedPhone(phone: String): Boolean

    suspend fun isDuplicatedNickname(nickname: String): Boolean

    suspend fun join(user: User): User

    suspend fun getUserInfo(id: String): User

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(user: User): User

    suspend fun updatePassword(id: String, prePw: String, newPw: String): Boolean
}