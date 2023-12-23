package com.aga.data.data.repository.user.remote

import com.aga.domain.model.User

interface UserRemoteDataSource {
    suspend fun login(user: User): User

    suspend fun isDuplicatedId(id: String): Boolean
}