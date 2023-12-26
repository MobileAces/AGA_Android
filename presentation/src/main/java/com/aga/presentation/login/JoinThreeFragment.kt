package com.aga.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentJoinThreeBinding


class JoinThreeFragment : BaseFragment<FragmentJoinThreeBinding>(
    FragmentJoinThreeBinding::bind, R.layout.fragment_join_three
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

    private fun registerObserver(){
        viewModel.isValidNick.observe(viewLifecycleOwner){
            if (it == JoinViewModel.NICK_VALID){
                binding.etNickname.isErrorEnabled = false
                binding.etNickname.helperText = it
                binding.btnNext.isEnabled = true
            }else{
                binding.etNickname.isErrorEnabled = true
                binding.etNickname.error = it
                binding.etNickname.helperText = ""
                binding.btnNext.isEnabled = false
            }
        }
    }

    private fun registerListener(){

        binding.etNickname.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.isValidateNickname(p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnNext.setOnClickListener {

        }
    }
}