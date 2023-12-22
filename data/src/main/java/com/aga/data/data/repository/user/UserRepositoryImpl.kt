package com.aga.data.data.repository.user

import com.aga.data.data.repository.user.remote.UserRemoteDataSource
import com.aga.domain.model.User
import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun login(user: User): User {
        return remoteDataSource.login(user)
    }
}