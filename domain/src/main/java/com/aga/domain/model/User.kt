package com.aga.domain.model


data class User(
    val id: String,
    val pw: String = "",
    val nickname: String,
    val phone: String
)
