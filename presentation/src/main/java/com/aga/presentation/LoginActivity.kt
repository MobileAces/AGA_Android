package com.aga.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.base.Constants.JOINONE_TO_JOINTWO
import com.aga.presentation.base.Constants.LOGIN_TO_JOIN
import com.aga.presentation.base.Constants.PREF_AUTO_LOGIN
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.ActivityLoginBinding
import com.aga.presentation.login.JoinOneFragment
import com.aga.presentation.login.JoinTwoFragment
import com.aga.presentation.login.JoinViewModel
import com.aga.presentation.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {
    private val viewModel: JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 자동로그인 처리 부분이라 나중에 Splash로 빼주면 됩니다.
        if (PrefManager.read(PREF_AUTO_LOGIN, false)){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_login, LoginFragment())
            .commit()
    }

    fun navigate(id: Int){
        when(id){
            LOGIN_TO_JOIN -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_login, JoinOneFragment())
                    .addToBackStack("join")
                    .commit()
            }

            JOINONE_TO_JOINTWO -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_login, JoinTwoFragment())
                    .addToBackStack("join_two")
                    .commit()
            }
        }
    }
}