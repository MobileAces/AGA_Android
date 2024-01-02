package com.aga.presentation.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aga.domain.model.User
import com.aga.presentation.GroupActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentProfileChangeBinding
import com.aga.presentation.login.JoinViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

private const val TAG = "ProfileChangeFragment_AWSOME"
@AndroidEntryPoint
class ProfileChangeFragment : BaseFragment<FragmentProfileChangeBinding>(
    FragmentProfileChangeBinding::bind, R.layout.fragment_profile_change
) {
    private val viewModel: ProfileChangeViewModel by viewModels()
    private lateinit var activity: GroupActivity

    val auth = Firebase.auth
    var verificationId = ""
    var phoneNum = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as GroupActivity
        binding.btnApplyChange.isEnabled = false
        binding.btnVerify.isEnabled = false
        binding.btnValidationCode.isEnabled = false
        viewModel.getUserInfo(PrefManager.read(Constants.PREF_USER_ID, "")!!)

        registerObserver()
        registerListener()
    }

    private fun registerObserver(){
        viewModel.userInfo.observe(viewLifecycleOwner){
            binding.etId.editText!!.setText(it.id)
            binding.etNickname.editText!!.setText(it.nickname)
            binding.etPhone.editText!!.setText(it.phone)
        }

        viewModel.toastMsg.observe(viewLifecycleOwner){
            showToast(it)
        }

        viewModel.isValidPhone.observe(viewLifecycleOwner) {
            if (it == JoinViewModel.PHONE_VALID){
                binding.etPhone.isErrorEnabled = false
                binding.etPhone.helperText = it
                binding.btnValidationCode.isEnabled = true
            }else{
                binding.etPhone.isErrorEnabled = true
                binding.etPhone.error = it
                binding.etPhone.helperText = ""
                binding.btnValidationCode.isEnabled = false
            }
        }

        viewModel.isValidNick.observe(viewLifecycleOwner){
            if (it == JoinViewModel.NICK_VALID){
                binding.etNickname.isErrorEnabled = false
                binding.etNickname.helperText = it
            }else{
                binding.etNickname.isErrorEnabled = true
                binding.etNickname.error = it
                binding.etNickname.helperText = ""
            }
        }

        viewModel.isValidAllInput.observe(viewLifecycleOwner){
            binding.btnApplyChange.isEnabled = it
        }

        viewModel.updateResult.observe(viewLifecycleOwner){
            if (it)
                activity.navigate(Constants.PROFILECHANGE_TO_PROFILE)
        }
    }

    private fun registerListener(){
        binding.btnValidationCode.setOnClickListener {
            binding.btnVerify.isEnabled = true
            binding.etPhone.editText?.isEnabled = false

            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) { }
                override fun onVerificationFailed(e: FirebaseException) {
                    showToast("인증에 실패했습니다.")
                    binding.etPhone.editText?.isEnabled = true
                }
                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    this@ProfileChangeFragment.verificationId = verificationId
                    showToast("인증번호를 발송했습니다. 90초 내에 입력해주세요.")
                }
            }
            phoneNum = binding.etPhone.editText!!.text.toString()
            val phoneNumber = "+82$phoneNum".replace("-", "")
            Log.d(TAG, "registerListener: $phoneNumber")

            val optionsCompat =  PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(90L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
            auth.setLanguageCode("kr")
        }

        binding.btnVerify.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId, binding.etCode.editText?.text.toString())
            signInWithPhoneAuthCredential(credential)
        }

        binding.etPhone.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() == viewModel.userInfo.value?.phone){
                    binding.btnVerify.isEnabled = false
                    viewModel.phoneCheck = true
                    viewModel.phoneVerify(true)
                    binding.etPhone.isErrorEnabled = false
                    binding.etPhone.helperText = JoinViewModel.PHONE_VALID
                    binding.btnValidationCode.isEnabled = true
                }else{
                    binding.btnVerify.isEnabled = true
                    viewModel.phoneVerify(false)
                    viewModel.phoneCheck = false
                    viewModel.isValidatePhone(p0.toString().trim())
                }

            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etNickname.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() == viewModel.userInfo.value?.nickname){
                    viewModel.nicknameCheck = true
                    binding.etNickname.isErrorEnabled = false
                    binding.etNickname.helperText = JoinViewModel.NICK_VALID
                }else{
                    viewModel.nicknameCheck = false
                    viewModel.isValidateNickname(p0.toString().trim())
                }

            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnApplyChange.setOnClickListener {
            if (!binding.btnApplyChange.isEnabled){
                showToast("잘못된 값이 존재합니다.")
            }else {
                val user = User(
                    binding.etId.editText!!.text.toString().trim(),
                    "",
                    binding.etNickname.editText!!.text.toString().trim(),
                    binding.etPhone.editText!!.text.toString().trim()
                )
                viewModel.updateUser(user)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    showToast("인증에 성공했습니다.")
                    viewModel.phoneVerify(true)
                    binding.etPhone.editText?.isEnabled = false
                }
                else {
                    //인증실패
                    showToast("인증에 실패했습니다.")
                    viewModel.phoneVerify(false)
                    binding.etPhone.editText?.isEnabled = true
                }
            }
    }
}