package com.aga.presentation

import android.os.Bundle
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.databinding.ActivityLoginBinding
import com.aga.presentation.login.LoginFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_login, LoginFragment())
            .commit()
    }
}