package com.aga.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.usecase.team.GetTeamByTeamIdUseCase
import com.aga.domain.usecase.teammember.GetTeamMembersByTeamIdUseCase
import com.aga.presentation.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getTeamByTeamIdUseCase: GetTeamByTeamIdUseCase,
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase
): ViewModel(){

    private val _teamInfo = MutableLiveData<Team>()
    val teamInfo: LiveData<Team>
        get() = _teamInfo

    private val _teamMembers = MutableLiveData<List<TeamMember>>()
    val teamMembers: LiveData<List<TeamMember>>
        get() = _teamMembers

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    fun getTeamInfoByTeamId(teamId: String){
        viewModelScope.launch {
            try {
                val response = getTeamByTeamIdUseCase.invoke(teamId)
                if (response.teamId != "FAIL"){
                    _teamInfo.value = response
                }else{
                    _toastMsg.value = RESPONSE_ERR
                }
            }catch (e: Exception){
                //네트워크 에러
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun getTeamMemberByTeamId(teamId: String){
        viewModelScope.launch {
            try {
                _teamMembers.value= getTeamMembersByTeamIdUseCase.invoke(teamId)
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    companion object{
        const val RESPONSE_ERR = "정보를 불러오지 못했습니다."
    }
}