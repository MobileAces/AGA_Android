package com.aga.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.User
import com.aga.domain.usecase.user.DeleteAccountUseCase
import com.aga.domain.usecase.user.GetUserInfoUseCase
import com.aga.presentation.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
): ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    private val _deleteAccountResult = MutableLiveData<Boolean>(false)
    val deleteAccountResult: LiveData<Boolean>
        get() = _deleteAccountResult

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    fun getUserInfo(id: String){
        viewModelScope.launch {
            try {
                val response = getUserInfoUseCase.invoke(id)
                if (response.id != "FAIL")
                    _userInfo.value = response
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun deleteAccount(id: String){
        viewModelScope.launch {
            try {
                _deleteAccountResult.value = deleteAccountUseCase.invoke(id)
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }
}