package com.aga.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.TeamMember
import com.aga.domain.usecase.teammember.GetTeamMembersByTeamIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "MainViewModel_AWSOME"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase
): ViewModel() {
    var teamId: Int = -1
    var teamMaster: String = ""

    var periodStatisticsSelectedMemberList = listOf<String>()
    var periodStatisticsStartDate = ""
    var periodStatisticsEndDate = ""

    var dailyStatisticsDate = ""

    val periodStatisticsRequest = MutableLiveData<Boolean>(false)
    val dailyStatisticsRequest = MutableLiveData<Boolean>(false)

    private val _authorizedMemberList = MutableLiveData<List<TeamMember>>()
    val authorizedMemberList: LiveData<List<TeamMember>>
        get() = _authorizedMemberList
    
    private val _teamMemberList = MutableLiveData<List<TeamMember>>()
    val teamMemberList: LiveData<List<TeamMember>>
        get() = _teamMemberList

    fun loadAuthorizedMember(teamId: Int){
        viewModelScope.launch {
            try {
                val response = getTeamMembersByTeamIdUseCase.invoke(teamId.toString())
                _teamMemberList.value = response
                _authorizedMemberList.value = response.filter {
                    Log.d(TAG, "loadAuthorizedMember: ${it.userNickname}, ${it.authority}")
                    if (it.authority == 2)
                        teamMaster = it.userId
                    it.authority > 0
                }
            }catch (e: Exception){
                Log.d(TAG, "loadAuthorizedMember: ${e.message}")
            }
        }
    }

    fun isAuthorizedMember(userId: String): Boolean{
        authorizedMemberList.value?.forEach {
            if(it.userId == userId)
                return true
        }
        return false
    }
}