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
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.presentation.MainActivity
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentSettingChangeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SettingChangeFragment_AWSOME"
@AndroidEntryPoint
class SettingChangeFragment : BaseFragment<FragmentSettingChangeBinding>(
    FragmentSettingChangeBinding::bind, R.layout.fragment_setting_change
) {
    private val viewModel: SettingChangeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = _activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.findViewById<BottomNavigationView>(R.id.bnv_main).visibility = View.GONE

        initView()

        registerObserver()
        registerListener()
    }

    private fun initView(){
        viewModel.getTeamInfoByTeamId(mainViewModel.teamId.toString())
        viewModel.getTeamMemberByTeamId(mainViewModel.teamId.toString())
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun registerObserver(){
        viewModel.teamInfo.observe(viewLifecycleOwner){
            binding.etGroupName.editText!!.setText(it.teamName)
            binding.etGroupIntroduction.editText!!.setText(it.teamInfo)
            binding.tvGroupCreateDate.setText(DateFormatUtil.formatGroupCreateDate(it.teamCreateDate))
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
                        mainViewModel.teamId.toString(),
                        "",
                        binding.etGroupName.editText!!.text.toString().trim(),
                        binding.etGroupIntroduction.editText!!.text.toString().trim(),
                        mainViewModel.teamMaster
                    )
                )
            }else{
                showToast("모든 값을 입력해주세요.")
            }

        }
    }

}