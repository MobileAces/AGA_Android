package com.aga.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.JOINONE_TO_JOINTWO
import com.aga.presentation.databinding.FragmentJoinOneBinding

private const val TAG = "JoinOneFragment_AGA"
class JoinOneFragment : BaseFragment<FragmentJoinOneBinding>(
    FragmentJoinOneBinding::bind, R.layout.fragment_join_one
) {
    private val viewModel: JoinViewModel by activityViewModels()
    private lateinit var activity: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as LoginActivity
        binding.btnNext.isEnabled = false

        registerObserver()
        registerListener()
    }

    override fun onResume() {
        super.onResume()
        val userCache = viewModel.userInfo
        if (userCache.id != ""){
            binding.etId.editText!!.setText(userCache.id)
            binding.etPw.editText!!.setText(userCache.pw)
            binding.etPwRe.editText!!.setText(userCache.pw)
        }
    }

    private fun registerObserver(){
        viewModel.isValidId.observe(viewLifecycleOwner){
            //유효한 아이디
            if (it == JoinViewModel.ID_VALID){
                binding.etId.isErrorEnabled = false
                binding.etId.helperText = it
            }else{
                binding.etId.isErrorEnabled = true
                binding.etId.helperText = ""
                binding.etId.error = it
            }
        }

        viewModel.isValidPw.observe(viewLifecycleOwner){
            viewModel.isValidateAllInput()
            if (it == JoinViewModel.PW_VALID) {
                binding.etPw.helperText = it
            }
            else{
                binding.etPw.isErrorEnabled = true
                binding.etPw.helperText = ""
                binding.etPw.error = it
            }
        }

        viewModel.isValidAllInput.observe(viewLifecycleOwner){
            binding.btnNext.isEnabled = it
            Log.d(TAG, "registerObserver: $it")
        }

    }

    private fun registerListener(){
        binding.btnNext.setOnClickListener {
            viewModel.finishPageOne(
                binding.etId.editText!!.text.toString().trim(),
                binding.etPwRe.editText!!.text.toString().trim()
            )

            activity.navigate(JOINONE_TO_JOINTWO)
        }

        binding.etId.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.isValidateId(p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etPw.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.isValidPw(p0.toString().trim())
                isSamePw(p0.toString().trim(), binding.etPwRe.editText!!.text.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etPwRe.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isSamePw(binding.etPw.editText!!.text.toString().trim(), p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun isSamePw(pw: String, pwRe: String){
        if (pwRe == ""){
            viewModel.isValidatePwRe(false)
            return
        }
        if (pw == pwRe){
            binding.etPwRe.isErrorEnabled = false
            binding.etPwRe.helperText = getString(R.string.join_rule_pw_re_same)
            viewModel.isValidatePwRe(true)
        }else{
            binding.etPwRe.isErrorEnabled = true
            binding.etPwRe.helperText = ""
            binding.etPwRe.error = getString(R.string.join_rule_pw_re_non_same)
            viewModel.isValidatePwRe(false)
        }
    }
}