package com.aga.data.data.model.teamlist

data class TeamListByUserResponse(
    val message: String,
    val code: Int,
    val datalist: List<TeamByUserResponse>
)
