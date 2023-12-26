package com.aga.domain.repository

import com.aga.domain.model.User

interface UserRepository {
    suspend fun login(user: User): User

    suspend fun isDuplicatedId(id: String): Boolean

    suspend fun isDuplicatedPhone(phone: String): Boolean

    suspend fun isDuplicatedNickname(nickname: String): Boolean

    suspend fun join(user: User): User
}