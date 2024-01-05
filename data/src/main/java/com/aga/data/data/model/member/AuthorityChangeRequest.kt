package com.aga.data.data.model.member

data class AuthorityChangeRequest(
    val teamId: Int,
    val userId: String,
    val authority: Int
)
