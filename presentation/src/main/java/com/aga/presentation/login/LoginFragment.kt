package com.aga.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aga.presentation.LoginActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind, R.layout.fragment_login
) {

    private val viewModel: LoginViewModel by viewModels()
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