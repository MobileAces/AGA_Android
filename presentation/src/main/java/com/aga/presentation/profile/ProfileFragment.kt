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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.aga.domain.model.User
import com.aga.presentation.GroupActivity
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentProfileBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ProfileFragment_AWSOME"
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::bind, R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var activity: GroupActivity
    private var isSamePwAndPwRe = false
    private lateinit var changePwDialog: AlertDialog
    private lateinit var deleteAccountDialog: AlertDialog

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
                showToast("계정이 삭제 되었습니다.")
                deleteAccountDialog.dismiss()
                PrefManager.clear()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity.finish()
            }
        }

        viewModel.pwUpdateResult.observe(viewLifecycleOwner){
            if (it){
                showToast("비밀번호가 변경되었습니다.")
                changePwDialog.dismiss()
            }else{
                showToast("비밀번호 변경에 실패했습니다.")
            }
        }
    }

    private fun registerListener(){
        binding.tvDeleteAccount.setOnClickListener {
            showDeleteAccountDialog()
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
            showPwChangeDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showPwChangeDialog(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_change_password, activity.findViewById(R.id.cl_dialog)
        )
        val originalPw = view.findViewById<TextInputLayout>(R.id.et_original_pw)
        val newPw = view.findViewById<TextInputLayout>(R.id.et_pw)
        val newPwRe = view.findViewById<TextInputLayout>(R.id.et_pw_re)
        val btnCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)

        builder.setView(view)
        changePwDialog = builder.create()
        changePwDialog.apply {
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
            changePwDialog.dismiss()
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

    @SuppressLint("MissingInflatedId")
    private fun showDeleteAccountDialog(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_delete_account, activity.findViewById(R.id.cl_delete_account_dialog)
        )

        val etPw = view.findViewById<TextInputLayout>(R.id.et_pw)
        val btnCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)

        builder.setView(view)
        deleteAccountDialog = builder.create()
        deleteAccountDialog.apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }.show()


        btnCancel.setOnClickListener {
            deleteAccountDialog.dismiss()
        }

        btnSave.setOnClickListener {
            Log.d(TAG, "showDeleteAccountDialog: ${PrefManager.read(Constants.PREF_USER_ID, "")!!}, ${etPw.editText?.text.toString()}")
            viewModel.deleteAccount(User(PrefManager.read(Constants.PREF_USER_ID, "")!!, etPw.editText?.text.toString(), "", ""))
        }
    }
}