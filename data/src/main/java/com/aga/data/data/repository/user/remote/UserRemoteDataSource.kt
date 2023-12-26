package com.aga.data.data.repository.user.remote

import com.aga.domain.model.User

interface UserRemoteDataSource {
    suspend fun login(user: User): User

    suspend fun isDuplicatedId(id: String): Boolean

    suspend fun isDuplicatedPhone(phone: String): Boolean

    suspend fun isDuplicatedNickname(nickname: String): Boolean

    suspend fun join(user: User): User
}