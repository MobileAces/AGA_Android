package com.aga.data.data.model.team

data class TeamInfoChangeRequest(
    val teamId: String,
    val teamName: String,
    val teamInfo: String,
    val teamMaster: String
)
