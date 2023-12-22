package com.aga.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aga.presentation.LoginActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind, R.layout.fragment_login
) {
    private lateinit var activity: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as LoginActivity

        binding.btnLogin.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

    }


}