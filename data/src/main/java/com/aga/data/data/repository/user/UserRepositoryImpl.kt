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

    override suspend fun isDuplicatedId(id: String): Boolean {
        return remoteDataSource.isDuplicatedId(id)
    }

    override suspend fun isDuplicatedPhone(phone: String): Boolean {
        return remoteDataSource.isDuplicatedPhone(phone)
    }

    override suspend fun isDuplicatedNickname(nickname: String): Boolean {
        return remoteDataSource.isDuplicatedNickname(nickname)
    }

    override suspend fun join(user: User): User {
        return remoteDataSource.join(user)
    }

    override suspend fun getUserInfo(id: String): User {
        return remoteDataSource.getUserInfo(id)
    }

    override suspend fun deleteUser(id: String): Boolean {
        return remoteDataSource.deleteUser(id)
    }
}