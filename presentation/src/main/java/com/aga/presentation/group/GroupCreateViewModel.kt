package com.aga.presentation.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.usecase.team.CreateTeamUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCreateViewModel @Inject constructor(
    private val createTeamUseCase: CreateTeamUseCase
) : ViewModel() {
    private var _createGroupResult = MutableLiveData<Boolean>()
    val createGroupResult: LiveData<Boolean>
        get() = _createGroupResult

    fun createGroup(teamName: String, teamInfo: String) {
        PrefManager.read(Constants.PREF_USER_ID, null)?.let { id ->
            viewModelScope.launch {
                val result = createTeamUseCase(teamName, teamInfo, id)
                _createGroupResult.postValue(result)
            }
        }
    }
}