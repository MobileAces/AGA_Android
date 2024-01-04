package com.aga.presentation.setting

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aga.domain.model.TeamMember
import com.aga.presentation.MainActivity
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.SETTING_TO_SETTINGCHANGE
import com.aga.presentation.databinding.FragmentSettingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SettingFragment_AWSOME"

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::bind, R.layout.fragment_setting
) {
    private val viewModel: SettingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var activity: MainActivity
    private lateinit var memberAdapter: TeamMemberAdapter
    private var members = arrayListOf<TeamMember>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = _activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memberAdapter = TeamMemberAdapter(members)
        binding.rvMember.apply {
            this.adapter = memberAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 4)
        }
        activity.findViewById<BottomNavigationView>(R.id.bnv_main).visibility = View.VISIBLE

        initView()

        registerObserver()
        registerListener()
    }

    private fun initView(){
        viewModel.getTeamInfoByTeamId(mainViewModel.teamId.toString())
        viewModel.getTeamMemberByTeamId(mainViewModel.teamId.toString())

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun registerObserver(){
        viewModel.teamInfo.observe(viewLifecycleOwner){
            binding.tvGroupName.setText(it.teamName)
            binding.tvGroupIntroduction.setText(it.teamInfo)
        }

        viewModel.teamMembers.observe(viewLifecycleOwner){
            members.clear()
            members.addAll(it.sorted())

            binding.rvMember.adapter!!.notifyDataSetChanged()
            binding.tvMemberCount.setText("${it.size}명")
        }

        viewModel.toastMsg.observe(viewLifecycleOwner){
            showToast(it)
        }
    }

    private fun registerListener(){
        binding.btnChangeSetting.setOnClickListener{
            activity.navigate(SETTING_TO_SETTINGCHANGE)
        }
    }

}