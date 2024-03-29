package com.aga.presentation.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.model.User
import com.aga.domain.usecase.invite.GetInviteCodeUseCase
import com.aga.domain.usecase.team.DeleteTeamUseCase
import com.aga.domain.usecase.team.GetTeamByTeamIdUseCase
import com.aga.domain.usecase.team.ModifyTeamInfoUseCase
import com.aga.domain.usecase.teammember.GetTeamMembersByTeamIdUseCase
import com.aga.domain.usecase.teammember.LeaveTeamUseCase
import com.aga.domain.usecase.teammember.ModifyAuthorityUseCase
import com.aga.domain.usecase.user.LoginUseCase
import com.aga.presentation.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingViewModel_AWSOME"
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getTeamByTeamIdUseCase: GetTeamByTeamIdUseCase,
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase,
    private val deleteTeamUseCase: DeleteTeamUseCase,
    private val loginUseCase: LoginUseCase,
    private val leaveTeamUseCase: LeaveTeamUseCase,
    private val modifyTeamInfoUseCase: ModifyTeamInfoUseCase,
    private val modifyAuthorityUseCase: ModifyAuthorityUseCase,
    private val getInviteCodeUseCase: GetInviteCodeUseCase
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

    private val _teamDeleteResult = MutableLiveData<Boolean>()
    val teamDeleteResult: LiveData<Boolean>
        get() = _teamDeleteResult

    private val _leaveTeamResult = MutableLiveData<Boolean>()
    val leaveTeamResult: LiveData<Boolean>
        get() = _leaveTeamResult

    private val _expelMemberResult = MutableLiveData<Boolean>()
    val expelMemberResult: LiveData<Boolean>
        get() = _expelMemberResult

    private val _delegateTeamMasterResult = MutableLiveData<Boolean>()
    val delegateTeamMasterResult : LiveData<Boolean>
        get() = _delegateTeamMasterResult

    private val _modifyAuthorityResult = MutableLiveData<Boolean>()
    val modifyAuthorityResult: LiveData<Boolean>
        get() = _modifyAuthorityResult

    private val _inviteCodeResult = MutableLiveData<String>()
    val inviteCodeResult: LiveData<String>
        get() = _inviteCodeResult

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

    fun deleteTeam(masterId: String, masterPw: String, teamId: Int){
        viewModelScope.launch {
            try {
                if(loginUseCase.invoke(User(masterId, masterPw, "", "")) != "LOGIN_FAIL") {
                    _teamDeleteResult.value = deleteTeamUseCase.invoke(teamId)
                }else{
                    _toastMsg.value = ACCOUNT_ERR
                }
            }catch (e: Exception){
                Log.d(TAG, "deleteTeam: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun leaveTeam(userId: String, userPw: String, teamId: Int ){
        viewModelScope.launch {
            try {
                if(loginUseCase.invoke(User(userId, userPw, "", "")) != "LOGIN_FAIL") {
                    _leaveTeamResult.value = leaveTeamUseCase.invoke(teamId, userId)
                }else{
                    _toastMsg.value = ACCOUNT_ERR
                }
            }catch (e: Exception){
                Log.d(TAG, "deleteTeam: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun expelMember(teamId: Int, userId: String){
        viewModelScope.launch {
            try {
                _expelMemberResult.value = leaveTeamUseCase.invoke(teamId, userId)
            }catch (e: Exception){
                Log.d(TAG, "expelMember: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun delegateTeamMaster(team: Team){
        viewModelScope.launch {
            try {
                _delegateTeamMasterResult.value = modifyTeamInfoUseCase.invoke(team)
            }catch (e: Exception){
                Log.d(TAG, "delegateTeamMaster: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun modifyAuthority(teamMemberWithTeam: TeamMemberWithTeam){
        viewModelScope.launch {
            try {
                _modifyAuthorityResult.value = modifyAuthorityUseCase.invoke(teamMemberWithTeam)
            }catch (e: Exception){
                Log.d(TAG, "modifyAuthority: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun getInviteCode(teamId: Int){
        viewModelScope.launch {
            try {
                val response = getInviteCodeUseCase.invoke(teamId)
                if (response == "CODE_FAIL")
                    _toastMsg.value = CODE_ERR
                else
                    _inviteCodeResult.value = response
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    companion object{
        const val RESPONSE_ERR = "정보를 불러오지 못했습니다."
        const val ACCOUNT_ERR = "비밀번호가 잘못됐습니다."
        const val CODE_ERR = "초대코드 발급에 실패했습니다."
    }
}