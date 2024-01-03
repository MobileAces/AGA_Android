package com.aga.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.aga.domain.model.User
import com.aga.presentation.GroupActivity
import com.aga.presentation.LoginActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.LOGIN_TO_JOIN
import com.aga.presentation.base.Constants.PREF_AUTO_LOGIN
import com.aga.presentation.base.Constants.PREF_USER_ID
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind, R.layout.fragment_login
) {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var activity: LoginActivity
    private var autoLogin = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as LoginActivity

        //뒤로가기 2번 눌러 종료
        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, activity.onBackPressedCallback)

        registerListener()
        registerObserver()

    }

    private fun registerListener(){

        binding.btnLogin.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val id = binding.etId.editText!!.text.toString().trim()
            val pw = binding.etPw.editText!!.text.toString().trim()

            if (id.isBlank() || pw.isBlank()){
                showToast("아이디 비밀번호를 모두 입력하세요")
                binding.llInput.startAnimation(vibration)
            }else{
                viewModel.login(User(id, pw, "", ""))
            }
        }

        binding.cbAutoLogin.setOnCheckedChangeListener { compoundButton, b ->
            compoundButton.startAnimation(vibration)
            autoLogin = b
        }

        binding.tvJoin.setOnClickListener {
            activity.navigate(LOGIN_TO_JOIN)
        }

        binding.etId.editText!!.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                hideKeyboard()
                true
            }else
                false
        }

        binding.etPw.editText!!.setOnKeyListener { view, i, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                hideKeyboard()
                true
            }else
                false
        }
    }

    private fun registerObserver(){
        viewModel.loginResult.observe(viewLifecycleOwner){
            when(it){
                "LOGIN_FAIL" -> {
                    showToast("계정 정보가 잘못되었습니다.")
                }
                Constants.NET_ERR -> {
                    showToast(Constants.NET_ERR)
                }
                else -> {
                    showToast("환영합니다.")
                    PrefManager.write(PREF_AUTO_LOGIN, autoLogin)
                    PrefManager.write(PREF_USER_ID, it)

                    Handler().postDelayed({
                        val intent = Intent(activity, GroupActivity::class.java)
                        startActivity(intent)
                        activity.finish()
                    }, 500)
                }
            }
        }
    }

}