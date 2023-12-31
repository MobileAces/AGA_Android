package com.aga.domain.usecase.user

import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(id: String): Boolean{
        return repository.deleteUser(id)
    }
}