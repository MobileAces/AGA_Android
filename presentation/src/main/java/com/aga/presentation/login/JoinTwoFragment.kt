package com.aga.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.JOINTWO_TO_JOINTHREE
import com.aga.presentation.databinding.FragmentJoinTwoBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

private const val TAG = "JoinTwoFragment_AWSOME"
class JoinTwoFragment : BaseFragment<FragmentJoinTwoBinding>(
    FragmentJoinTwoBinding::bind, R.layout.fragment_join_two
) {
    private val viewModel: JoinViewModel by activityViewModels()
    private lateinit var activity: LoginActivity
    val auth = Firebase.auth
    var verificationId = ""
    var phoneNum = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as LoginActivity
        binding.btnNext.isEnabled = false
        binding.btnValidationCode.isEnabled = false
        binding.btnVerify.isEnabled = false

        registerObserver()
        registerListener()
    }

    override fun onResume() {
        super.onResume()
        val userCache = viewModel.userInfo
        if (userCache.phone != ""){
            binding.etPhone.editText!!.setText(userCache.phone)
            binding.btnNext.isEnabled = true
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
                    this@JoinTwoFragment.verificationId = verificationId
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
                viewModel.isValidatePhone(p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnNext.setOnClickListener {
            activity.navigate(JOINTWO_TO_JOINTHREE)
        }
    }

    private fun registerObserver(){
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
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    showToast("인증에 성공했습니다.")
                    binding.btnNext.isEnabled = true
                    binding.etPhone.editText?.isEnabled = false
                    viewModel.finishPageTwo(phoneNum)
                }
                else {
                    //인증실패
                    showToast("인증에 실패했습니다.")
                    binding.etPhone.editText?.isEnabled = true
                }
            }
    }
}