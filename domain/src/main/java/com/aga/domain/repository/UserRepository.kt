package com.aga.domain.repository

import com.aga.domain.model.User

interface UserRepository {
    suspend fun login(user: User): User
}