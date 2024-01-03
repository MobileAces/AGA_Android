package com.aga.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.User
import com.aga.domain.usecase.user.DeleteAccountUseCase
import com.aga.domain.usecase.user.GetUserInfoUseCase
import com.aga.domain.usecase.user.LoginUseCase
import com.aga.domain.usecase.user.UpdatePasswordUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.login.JoinViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

private const val TAG = "ProfileViewModel_AWSOME"
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val loginUseCase: LoginUseCase
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

    private val _pwUpdateResult = MutableLiveData<String>()
    val pwUpdateResult: LiveData<String>
        get() = _pwUpdateResult

    private val _loginResult = MutableLiveData<Boolean>(false)
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    fun getUserInfo(id: String){
        viewModelScope.launch {
            try {
                val response = getUserInfoUseCase.invoke(id)
                _userInfo.value = response
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun isValidPw(pw: String){
        if(Pattern.matches(PW_REG, pw)) {
            _isValidPw.value = PW_VALID
        }
        else {
            _isValidPw.value = PW_RULE
        }
    }

    fun updatePassword(id: String, prePw: String, newPw: String){
        viewModelScope.launch {
            try {
               _pwUpdateResult.value = updatePasswordUseCase.invoke(id, prePw, newPw)
            }catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
                Log.d(TAG, "updatePassword: ${e.message}")
            }
        }
    }

    fun deleteAccount(user: User){
        viewModelScope.launch {
            try {
                val loginRst = loginUseCase.invoke(user)
                if (loginRst == "LOGIN_FAIL"){
                    _toastMsg.value = "비밀번호가 잘못되었습니다."
                }else{
                    _deleteAccountResult.value = deleteAccountUseCase.invoke(user.id)
                }
            } catch (e: Exception){
                _toastMsg.value = Constants.NET_ERR
                Log.d(TAG, "deleteAccount: ${e.message}")
            }
        }
    }

    companion object{
        const val PW_REG = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+])[a-zA-Z0-9!@#\$%^&*()_+]{8,}\$"
        const val PW_RULE = "8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다."
        const val PW_VALID = "유효한 비밀번호입니다."

        const val PW_CHANGE_SUCCESS = "비밀번호가 변경되었습니다."
        const val PW_CHANGE_SAME = "기존 비밀번호와 동일한 비밀번호를 사용할 수 없습니다."
        const val PW_CHANGE_WRONG = "현재 비밀번호가 잘못되었습니다."
    }
}