package com.aga.domain.usecase.user

import com.aga.domain.model.User
import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): User{
        return repository.getUserInfo(id)
    }
}