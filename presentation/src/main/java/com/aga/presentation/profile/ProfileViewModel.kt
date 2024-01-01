package com.aga.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.User
import com.aga.domain.usecase.user.DeleteAccountUseCase
import com.aga.domain.usecase.user.GetUserInfoUseCase
import com.aga.domain.usecase.user.UpdatePasswordUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.login.JoinViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase
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

    private val _isValidPw = MutableLiveData<String>()
    val isValidPw: LiveData<String>
        get() = _isValidPw

    private val _pwUpdateResult = MutableLiveData<Boolean>()
    val pwUpdateResult: LiveData<Boolean>
        get() = _pwUpdateResult

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

    fun isValidPw(pw: String){
        if(Pattern.matches(JoinViewModel.PW_REG, pw)) {
            _isValidPw.value = JoinViewModel.PW_VALID
        }
        else {
            _isValidPw.value = JoinViewModel.PW_RULE
        }
    }

    fun updatePassword(id: String, prePw: String, newPw: String){
        viewModelScope.launch {
            try {
                _pwUpdateResult.value = updatePasswordUseCase.invoke(id, prePw, newPw)
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    companion object{
        const val PW_REG = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+])[a-zA-Z0-9!@#\$%^&*()_+]{8,}\$"
        const val PW_RULE = "8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다."
        const val PW_VALID = "유효한 비밀번호입니다."
    }
}