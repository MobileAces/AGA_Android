package com.aga.presentation.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.TeamWithMember
import com.aga.domain.usecase.invite.ConfirmInviteCodeUseCase
import com.aga.domain.usecase.teammember.GetTeamListByUserIdUseCase
import com.aga.domain.usecase.teammember.RegisterTeamMemberUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getTeamListByUserIdUseCase: GetTeamListByUserIdUseCase,
    private val confirmInviteCodeUseCase: ConfirmInviteCodeUseCase,
    private val registerTeamMemberUseCase: RegisterTeamMemberUseCase
) : ViewModel() {
    private var _groupList = MutableLiveData<List<TeamWithMember>>()
    val groupList: LiveData<List<TeamWithMember>>
        get() = _groupList

    private val _registerTeamResult = MutableLiveData<Boolean>(false)
    val registerTeamResult: LiveData<Boolean>
        get() = _registerTeamResult

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    fun getGroupList() {
        PrefManager.read(Constants.PREF_USER_ID, null)?.let { userId ->
            viewModelScope.launch {
                getTeamListByUserIdUseCase(userId).let {
                    _groupList.postValue(it)
                }
            }
        }
    }

    fun confirmInviteCode(code: String){
        viewModelScope.launch {
            try {
                val teamId = confirmInviteCodeUseCase.invoke(code)
                when(teamId){
                    -1 -> {
                        _toastMsg.value = "잘못된 초대 코드입니다."
                    }
                    -2 -> {
                        _toastMsg.value = "만료된 초대 코드입니다. 재발급을 요청하세요."
                    }
                    else -> {
                        _registerTeamResult.value = registerTeamMemberUseCase.invoke(teamId, PrefManager.read(Constants.PREF_USER_ID, "")!!)
                    }
                }
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }
}