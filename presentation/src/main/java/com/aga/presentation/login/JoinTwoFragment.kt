package com.aga.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentJoinTwoBinding

class JoinTwoFragment : BaseFragment<FragmentJoinTwoBinding>(
    FragmentJoinTwoBinding::bind, R.layout.fragment_join_two
) {
    private val viewModel: JoinViewModel by activityViewModels()
    private lateinit var activity: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as LoginActivity
    }
}