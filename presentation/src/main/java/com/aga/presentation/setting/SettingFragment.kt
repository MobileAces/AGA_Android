package com.aga.presentation.setting

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aga.domain.model.TeamMember
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SettingFragment_AWSOME"

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(
    FragmentSettingBinding::bind, R.layout.fragment_setting
) {
    private val viewModel: SettingViewModel by viewModels()
    private lateinit var activity: MainActivity
    private lateinit var memberAdapter: TeamMemberAdapter
    private var members = arrayListOf<TeamMember>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = _activity as MainActivity
        members.add(TeamMember("1", "id", 1))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memberAdapter = TeamMemberAdapter(members)
        binding.rvMember.apply {
            this.adapter = memberAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 4)
        }

        initView()

        registerObserver()
        registerListener()
    }

    private fun initView(){
        viewModel.getTeamInfoByTeamId("2")
        viewModel.getTeamMemberByTeamId("2")

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

    }

}