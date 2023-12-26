package com.aga.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.User
import com.aga.domain.usecase.user.IdDuplicatedUseCase
import com.aga.domain.usecase.user.NicknameDuplicatedUseCase
import com.aga.domain.usecase.user.PhoneDuplicatedUseCase
import com.aga.presentation.base.Constants.NET_ERR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

private const val TAG = "JoinViewModel_AGA"
@HiltViewModel
class JoinViewModel @Inject constructor(
    private val idDuplicatedUseCase: IdDuplicatedUseCase,
    private val phoneDuplicatedUseCase: PhoneDuplicatedUseCase,
    private val nicknameDuplicatedUseCase: NicknameDuplicatedUseCase
): ViewModel() {

    var userInfo = User("","","","")

    private val _isValidId = MutableLiveData<String>()
    val isValidId: LiveData<String>
        get() = _isValidId

    private val _isValidPw = MutableLiveData<String>()
    val isValidPw: LiveData<String>
        get() = _isValidPw

    private val _isValidAllInput = MutableLiveData<Boolean>(false)
    val isValidAllInput: LiveData<Boolean>
        get() = _isValidAllInput

    private val _isValidPhone = MutableLiveData<String>()
    val isValidPhone: LiveData<String>
        get() = _isValidPhone

    private val _isValidNick = MutableLiveData<String>()
    val isValidNick: LiveData<String>
        get() = _isValidNick

    private var idCheck = false
    private var pwCheck = false
    private var pwReCheck = false

    //여기부터 첫번째 페이지
    fun isValidateId(id: String){
        viewModelScope.launch {
            try {
                //유효성에 맞지 않을 때
                if(!(Pattern.matches(ID_REG, id))) {
                    _isValidId.value = ID_RULE
                    idCheck = false
                }
                else{
                    //유효성에 맞지만 중복된 아이디일 때
                    if (idDuplicatedUseCase.invoke(id)) {
                        _isValidId.value = ID_DUP
                        idCheck = false
                    }
                    //유효하고 중복 되지 않은 아이디일 때
                    else {
                        _isValidId.value = ID_VALID
                        idCheck= true
                    }
                }
            }catch (e: Exception){
                Log.d(TAG, "isValidateId: ${e.printStackTrace()}")
                _isValidId.value = NET_ERR
                idCheck = false
            }
            isValidateAllInput()
        }
    }

    fun isValidPw(pw: String){
        if(Pattern.matches(PW_REG, pw)) {
            _isValidPw.value = PW_VALID
            pwCheck = true
        }
        else {
            _isValidPw.value = PW_RULE
            pwCheck = false
        }
    }

    fun isValidatePwRe(b: Boolean){
        pwReCheck = b
        isValidateAllInput()
    }

    fun isValidateAllInput(){
        _isValidAllInput.value = idCheck && pwCheck && pwReCheck
    }

    fun finishPageOne(id: String, pw: String){
        userInfo.id = id
        userInfo.pw = pw
    }

    //여기부터 두번째 페이지
    fun isValidatePhone(phone: String){
        viewModelScope.launch {
            try {
                //유효성에 맞지 않을 때
                if(!(Pattern.matches(PHONE_REG, phone))) {
                    _isValidPhone.value = PHONE_RULE
                }
                else{
                    //유효성에 맞지만 중복된 아이디일 때
                    if (phoneDuplicatedUseCase.invoke(phone)) {
                        _isValidPhone.value = PHONE_DUP
                    }
                    //유효하고 중복 되지 않은 아이디일 때
                    else {
                        _isValidPhone.value = PHONE_VALID
                    }
                }
            }catch (e: Exception){
                Log.d(TAG, "isValidatePhone: ${e.printStackTrace()}")
                _isValidPhone.value = NET_ERR
            }
        }
    }

    fun finishPageTwo(phone: String){
        userInfo.phone = phone
    }

    //여기부터 세번째 페이지
    fun isValidateNickname(nickname: String){
        viewModelScope.launch {
            try {
                //유효성에 맞지 않을 때
                if(!(Pattern.matches(NICK_REG, nickname))) {
                    _isValidNick.value = NICK_RULE
                }
                else{
                    //유효성에 맞지만 중복된 아이디일 때
                    if (nicknameDuplicatedUseCase.invoke(nickname)) {
                        _isValidNick.value = NICK_DUP
                    }
                    //유효하고 중복 되지 않은 아이디일 때
                    else {
                        _isValidNick.value = NICK_VALID
                    }
                }
            }catch (e: Exception){
                Log.d(TAG, "isValidateNick: ${e.printStackTrace()}")
                _isValidNick.value = NET_ERR
            }
        }
    }


    companion object{
        const val ID_REG = "^[a-zA-Z0-9]{5,12}\$"
        const val ID_DUP = "중복된 아이디입니다."
        const val ID_RULE = "5~20자, 영문 숫자로 이루어져야 합니다."
        const val ID_VALID = "유효한 아이디입니다."

        const val PW_REG = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+])[a-zA-Z0-9!@#\$%^&*()_+]{8,}\$"
        const val PW_RULE = "8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다."
        const val PW_VALID = "유효한 비밀번호입니다."

        const val PHONE_REG = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}\$"
        const val PHONE_DUP = "이미 가입된 핸드폰 번호입니다."
        const val PHONE_RULE = "010-xxxx-xxxx의 형식으로 입력해주세요."
        const val PHONE_VALID = "사용 가능한 핸드폰 번호입니다."

        const val NICK_REG = "^[a-zA-Z0-9가-힣]{2,10}\$"
        const val NICK_DUP = "중복된 닉네임입니다."
        const val NICK_RULE = "10자 이내, 문자와 숫자로 이루어져야 합니다."
        const val NICK_VALID = "사용 가능한 닉네임입니다."

    }
}