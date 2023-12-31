package com.aga.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aga.presentation.GroupActivity
import com.aga.presentation.LoginActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::bind, R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var activity: GroupActivity

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
        binding.btnDeleteAccount.setOnClickListener {
            viewModel.deleteAccount(PrefManager.read(Constants.PREF_USER_ID, "")!!)
        }

        binding.btnEditProfile.setOnClickListener {

        }

        binding.btnLogout.setOnClickListener {
            PrefManager.clear()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity.finish()
        }
    }
}