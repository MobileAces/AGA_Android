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
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.FragmentSettingChangeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SettingChangeFragment_AWSOME"
@AndroidEntryPoint
class SettingChangeFragment : BaseFragment<FragmentSettingChangeBinding>(
    FragmentSettingChangeBinding::bind, R.layout.fragment_setting_change
) {
    private val viewModel: SettingChangeViewModel by viewModels()
    private lateinit var activity: MainActivity
    private lateinit var memberAdapter: TeamMemberAdapter
    private var members = arrayListOf<TeamMember>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = _activity as MainActivity
        memberAdapter = TeamMemberAdapter(members)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.findViewById<BottomNavigationView>(R.id.bnv_main).visibility = View.GONE
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

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun registerObserver(){
        viewModel.teamInfo.observe(viewLifecycleOwner){
            binding.etGroupName.editText!!.setText(it.teamName)
            binding.etGroupIntroduction.editText!!.setText(it.teamInfo)
            binding.tvGroupCreateDate.setText(DateFormatUtil.formatGroupCreateDate(it.teamCreateDate))
        }

        viewModel.teamMembers.observe(viewLifecycleOwner){
            members.clear()
            members.addAll(it.sorted())

            binding.rvMember.adapter!!.notifyDataSetChanged()
            binding.tvMemberCount.setText("${it.size}명")
        }

        viewModel.teamInfoChanged.observe(viewLifecycleOwner){
            if (it){
                activity.navigate(Constants.SETTINGCHANGE_TO_SETTING)
            }else{
                Log.d(TAG, "registerObserver: $it")
            }
        }

        viewModel.toastMsg.observe(viewLifecycleOwner){
            showToast(it)
        }
    }

    private fun registerListener(){
        binding.btnApplyChange.setOnClickListener {
            if ( binding.etGroupName.editText!!.text.toString().trim() != ""
                && binding.etGroupIntroduction.editText!!.text.toString().trim() != ""){
                viewModel.modifyTeamInfo(
                    Team(
                        "2",
                        "",
                        binding.etGroupName.editText!!.text.toString().trim(),
                        binding.etGroupIntroduction.editText!!.text.toString().trim(),
                        "user2"
                    )
                )
            }else{
                showToast("모든 값을 입력해주세요.")
            }

        }
    }

}