package com.aga.domain.model

data class TeamMember(
    val userId: String,
    val userNickname: String,
    val authority: Int
): Comparable<TeamMember>{
    override fun compareTo(other: TeamMember): Int {
        return other.authority - authority
    }

}
