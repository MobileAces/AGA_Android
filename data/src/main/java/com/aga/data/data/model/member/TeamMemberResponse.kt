package com.aga.data.data.model.member

data class TeamMemberResponse(
    val message: String,
    val code: Int,
    val teamId: String,
    val dataList: List<TeamMemberDataResponse>
)
