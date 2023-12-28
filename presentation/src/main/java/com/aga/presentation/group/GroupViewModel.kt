package com.aga.presentation.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.TeamWithMember
import com.aga.domain.usecase.teammember.GetTeamListByUserIdUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getTeamListByUserIdUseCase: GetTeamListByUserIdUseCase
) : ViewModel() {
    private var _groupList = MutableLiveData<List<TeamWithMember>>()
    val groupList: LiveData<List<TeamWithMember>>
        get() = _groupList

    fun getGroupList() {
        PrefManager.read(Constants.PREF_USER_ID, null)?.let { userId ->
            viewModelScope.launch {
                getTeamListByUserIdUseCase(userId).let {
                    _groupList.postValue(it)
                }
            }
        }
    }
}