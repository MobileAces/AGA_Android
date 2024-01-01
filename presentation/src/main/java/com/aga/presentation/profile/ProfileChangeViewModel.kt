package com.aga.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.User
import com.aga.domain.usecase.user.GetUserInfoUseCase
import com.aga.domain.usecase.user.IdDuplicatedUseCase
import com.aga.domain.usecase.user.NicknameDuplicatedUseCase
import com.aga.domain.usecase.user.PhoneDuplicatedUseCase
import com.aga.domain.usecase.user.UpdateUserUseCase
import com.aga.presentation.base.Constants
import com.aga.presentation.login.JoinViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

private const val TAG = "ProfileChangeViewModel_AWSOME"
@HiltViewModel
class ProfileChangeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val idDuplicatedUseCase: IdDuplicatedUseCase,
    private val phoneDuplicatedUseCase: PhoneDuplicatedUseCase,
    private val nicknameDuplicatedUseCase: NicknameDuplicatedUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    private val _isValidPw = MutableLiveData<String>()
    val isValidPw: LiveData<String>
        get() = _isValidPw

    private val _isValidPhone = MutableLiveData<String>()
    val isValidPhone: LiveData<String>
        get() = _isValidPhone

    private val _isValidNick = MutableLiveData<String>()
    val isValidNick: LiveData<String>
        get() = _isValidNick

    private val _isValidAllInput = MutableLiveData<Boolean>(false)
    val isValidAllInput: LiveData<Boolean>
        get() = _isValidAllInput

    private val _updateResult = MutableLiveData<Boolean>(false)
    val updateResult: LiveData<Boolean>
        get() = _updateResult

    private var phoneCheck = false
    private var nicknameCheck = false
    private var phoneVerified = false

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

    fun updateUser(user: User){
        viewModelScope.launch {
            try {
                val response = updateUserUseCase.invoke(user)
                if (response.id != "FAIL"){
                    _updateResult.value = true
                }else{
                    _toastMsg.value = UPDATE_FAIL
                }
            }catch (e: Exception){
                Log.d(TAG, "updateUser: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun isValidatePhone(phone: String){
        viewModelScope.launch {
            try {
                if (phone == userInfo.value?.phone){
                    _isValidPhone.value = JoinViewModel.PHONE_VALID
                    phoneCheck = true
                }else{
                    //유효성에 맞지 않을 때
                    if(!(Pattern.matches(JoinViewModel.PHONE_REG, phone))) {
                        _isValidPhone.value = JoinViewModel.PHONE_RULE
                        phoneCheck = false
                    }
                    else{
                        //유효성에 맞지만 중복된 아이디일 때
                        if (phoneDuplicatedUseCase.invoke(phone)) {
                            _isValidPhone.value = JoinViewModel.PHONE_DUP
                            phoneCheck = false
                        }
                        //유효하고 중복 되지 않은 아이디일 때
                        else {
                            _isValidPhone.value = JoinViewModel.PHONE_VALID
                            phoneCheck = true
                        }
                    }
                }
            }catch (e: Exception){
                Log.d(TAG, "isValidatePhone: ${e.printStackTrace()}")
                _isValidPhone.value = Constants.NET_ERR
                phoneCheck = false
            }
            isValidateAllInput()
        }
    }

    fun isValidateNickname(nickname: String){
        viewModelScope.launch {
            try {
                if (nickname == userInfo.value?.nickname){
                    _isValidNick.value = JoinViewModel.NICK_VALID
                    nicknameCheck = true
                }else{
                    //유효성에 맞지 않을 때
                    if(!(Pattern.matches(JoinViewModel.NICK_REG, nickname))) {
                        _isValidNick.value = JoinViewModel.NICK_RULE
                        nicknameCheck = false
                    }
                    else{
                        //유효성에 맞지만 중복된 아이디일 때
                        if (nicknameDuplicatedUseCase.invoke(nickname)) {
                            _isValidNick.value = JoinViewModel.NICK_DUP
                            nicknameCheck = false
                        }
                        //유효하고 중복 되지 않은 아이디일 때
                        else {
                            _isValidNick.value = JoinViewModel.NICK_VALID
                            nicknameCheck = true
                        }
                    }
                }
            }catch (e: Exception){
                Log.d(TAG, "isValidateNick: ${e.printStackTrace()}")
                _isValidNick.value = Constants.NET_ERR
                nicknameCheck = false
            }
            isValidateAllInput()
        }
    }

    private fun isValidateAllInput(){
        _isValidAllInput.value = phoneCheck && nicknameCheck && phoneVerified
    }

    fun phoneVerify(b: Boolean){
        phoneVerified = b
        isValidateAllInput()
    }

    companion object{
        const val UPDATE_FAIL = "정보 수정에 실패했습니다."
    }

}