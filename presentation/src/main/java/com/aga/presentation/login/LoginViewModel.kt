package com.aga.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.User
import com.aga.domain.usecase.user.LoginUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.NET_ERR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel_AGA"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String>
        get() = _loginResult

    fun login(user: User){
        viewModelScope.launch {
            try {
                val response = loginUseCase.invoke(user)
                _loginResult.value = response
            }catch (e: Exception){
                Log.d(TAG, "login: ${e.message}")
                _loginResult.value = NET_ERR
            }
        }
    }

    companion object{
        const val LOGIN_SUCCESS = "환영합니다."
        const val LOGIN_FAIL = "계정 정보가 잘못 되었습니다."

        const val WRONG_ACCOUNT_INFO = "LOGIN_FAIL"
    }
}