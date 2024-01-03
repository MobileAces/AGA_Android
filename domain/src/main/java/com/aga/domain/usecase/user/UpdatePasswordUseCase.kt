package com.aga.domain.usecase.user

import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String, prePw: String, newPw: String): String{
        return repository.updatePassword(id, prePw, newPw)
    }
}