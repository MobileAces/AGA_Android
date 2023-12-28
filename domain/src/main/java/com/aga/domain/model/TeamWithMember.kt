package com.aga.domain.model

data class TeamWithMember (
    val teamId: Int,
    val teamName: String,
    val teamInfo: String,
    val teamMaster: String,
    val memberList: List<TeamMember>
)