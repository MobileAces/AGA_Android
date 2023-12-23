package com.aga.domain.model


data class User(
    var id: String,
    var pw: String = "",
    var nickname: String,
    var phone: String
)
