package com.aga.presentation.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.usecase.team.GetTeamByTeamIdUseCase
import com.aga.domain.usecase.team.ModifyTeamInfoUseCase
import com.aga.domain.usecase.teammember.GetTeamMembersByTeamIdUseCase
import com.aga.presentation.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingChangeViewModel_AWSOME"

@HiltViewModel
class SettingChangeViewModel @Inject constructor(
    private val getTeamByTeamIdUseCase: GetTeamByTeamIdUseCase,
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase,
    private val modifyTeamInfoUseCase: ModifyTeamInfoUseCase
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

    private val _teamInfoChanged = MutableLiveData<Boolean>(false)
    val teamInfoChanged: LiveData<Boolean>
        get() = _teamInfoChanged

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

    fun modifyTeamInfo(team: Team){
        Log.d(TAG, "modifyTeamInfo: called")
        viewModelScope.launch {
            try {
                val response = modifyTeamInfoUseCase.invoke(team)
                Log.d(TAG, "modifyTeamInfo: $response")
                if (response){
                    _teamInfoChanged.value = response
                    _toastMsg.value = MODIFY_SUCCESS
                }else
                    _toastMsg.value = MODIFY_FAIL
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    companion object{
        const val RESPONSE_ERR = "정보를 불러오지 못했습니다."
        const val MODIFY_SUCCESS = "정보를 수정했습니다."
        const val MODIFY_FAIL = "수정에 실패했습니다."
    }
}