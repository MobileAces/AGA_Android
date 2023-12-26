package com.aga.domain.usecase.user

import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class phoneDuplicatedUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(phone: String): Boolean{
        return repository.isDuplicatedPhone(phone)
    }
}