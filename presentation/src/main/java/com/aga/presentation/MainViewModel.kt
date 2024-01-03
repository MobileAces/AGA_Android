package com.aga.presentation

import androidx.lifecycle.ViewModel
import com.aga.domain.model.TeamMember
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainViewModel: ViewModel() {
    var teamId: Int = -1
    lateinit var authorizedMemberList: List<TeamMember>
}