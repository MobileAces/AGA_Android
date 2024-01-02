package com.aga.domain.usecase.user

import com.aga.domain.model.User
import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): User{
        return repository.updateUser(user)
    }
}