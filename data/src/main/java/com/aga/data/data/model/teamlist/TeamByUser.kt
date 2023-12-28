package com.aga.data.data.model.teamlist

import com.aga.data.data.model.member.TeamMemberDataResponse

data class TeamByUser(
    val teamId: Int,
    val teamName: String,
    val teamInfo: String,
    val teamMaster: String,
    val userLists: List<TeamMemberDataResponse>
)
