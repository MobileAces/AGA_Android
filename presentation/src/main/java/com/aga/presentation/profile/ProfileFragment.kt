package com.aga.presentation.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.aga.presentation.GroupActivity
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentProfileBinding
import com.aga.presentation.login.JoinViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::bind, R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var activity: GroupActivity
    private var isSamePwAndPwRe = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = _activity as GroupActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserInfo(PrefManager.read(Constants.PREF_USER_ID, "")!!)
        registerObserver()
        registerListener()
    }

    private fun registerObserver(){
        viewModel.userInfo.observe(viewLifecycleOwner){
            binding.tvId.setText(it.id)
            binding.tvNickname.setText(it.nickname)
            binding.tvPhone.setText(it.phone)
        }

        viewModel.toastMsg.observe(viewLifecycleOwner){
            showToast(it)
        }

        viewModel.deleteAccountResult.observe(viewLifecycleOwner){
            if (it){
                PrefManager.clear()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity.finish()
            }
        }
    }

    private fun registerListener(){
        binding.tvDeleteAccount.setOnClickListener {
            viewModel.deleteAccount(PrefManager.read(Constants.PREF_USER_ID, "")!!)
        }

        binding.btnEditProfile.setOnClickListener {
            activity.navigate(Constants.PROFILE_TO_PROFILECHANGE)
        }

        binding.btnLogout.setOnClickListener {
            PrefManager.clear()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity.finish()
        }

        binding.btnChangePassword.setOnClickListener {
            showDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.dlg_change_password, activity.findViewById(R.id.cl_dialog)
        )
        val originalPw = view.findViewById<TextInputLayout>(R.id.et_original_pw)
        val newPw = view.findViewById<TextInputLayout>(R.id.et_pw)
        val newPwRe = view.findViewById<TextInputLayout>(R.id.et_pw_re)
        val btnCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)

        builder.setView(view)
        val dialog = builder.create()
        dialog.apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }.show()

        viewModel.isValidPw.observe(viewLifecycleOwner){
            if (it == ProfileViewModel.PW_VALID) {
                newPw.helperText = it
            }
            else{
                newPw.isErrorEnabled = true
                newPw.helperText = ""
                newPw.error = it
            }
        }

        newPw.editText!!.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.isValidPw(p0.toString().trim())
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        newPwRe.editText!!.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() == ""){
                    isSamePwAndPwRe = false
                }else{
                    if (newPw.editText!!.text.toString() == p0.toString()){
                        newPwRe.isErrorEnabled = false
                        newPwRe.helperText = getString(R.string.join_rule_pw_re_same)
                        isSamePwAndPwRe = true
                    }else{
                        newPwRe.isErrorEnabled = true
                        newPwRe.helperText = ""
                        newPwRe.error = getString(R.string.join_rule_pw_re_non_same)
                        isSamePwAndPwRe = false
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })



        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            if (viewModel.isValidPw.value == ProfileViewModel.PW_VALID && isSamePwAndPwRe){
                viewModel.updatePassword(PrefManager.read(Constants.PREF_USER_ID, "")!!,
                    originalPw.editText!!.text.toString(),
                    newPw.editText!!.text.toString())
            }else{
                showToast("잘못된 정보가 존재합니다.")
            }
        }
    }
}