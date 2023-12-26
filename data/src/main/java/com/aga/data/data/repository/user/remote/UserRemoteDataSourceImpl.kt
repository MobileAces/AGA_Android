package com.aga.data.data.repository.user.remote

import com.aga.data.data.api.UserService
import com.aga.data.data.model.mapper.toUser
import com.aga.data.data.model.mapper.toUserRequest
import com.aga.domain.model.User
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
): UserRemoteDataSource {
    override suspend fun login(user: User): User {
        return userService.login(user.toUserRequest()).toUser()
    }

    override suspend fun isDuplicatedId(id: String): Boolean {
        return userService.isDuplicatedId(id)
    }

    override suspend fun isDuplicatedPhone(phone: String): Boolean {
        return userService.isDuplicatedPhone(phone)
    }

    override suspend fun isDuplicatedNickname(nickname: String): Boolean {
        return userService.isDuplicatedNickname(nickname)
    }
}