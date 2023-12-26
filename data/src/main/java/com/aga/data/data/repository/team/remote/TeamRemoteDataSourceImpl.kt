package com.aga.data.data.repository.team.remote

import com.aga.data.data.api.TeamService
import javax.inject.Inject

class TeamRemoteDataSourceImpl @Inject constructor(
    private val teamService: TeamService
): TeamRemoteDataSource {

}