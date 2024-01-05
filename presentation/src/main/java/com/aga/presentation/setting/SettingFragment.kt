package com.aga.presentation.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aga.domain.model.TeamMember
import com.aga.domain.model.User
import com.aga.presentation.GroupActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.SETTING_TO_SETTINGCHANGE
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentSettingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
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

    private lateinit var deleteGroupDialog: AlertDialog
    private lateinit var exitGroupDialog: AlertDialog

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

        viewModel.teamDeleteResult.observe(viewLifecycleOwner){
            if (it){
                deleteGroupDialog.dismiss()
                val intent = Intent(activity, GroupActivity::class.java)
                startActivity(intent)
                activity.finish()
            }
        }

        viewModel.leaveTeamResult.observe(viewLifecycleOwner){
            if (it){
                exitGroupDialog.dismiss()
                val intent = Intent(activity, GroupActivity::class.java)
                startActivity(intent)
                activity.finish()
            }
        }
    }

    private fun registerListener(){
        binding.btnChangeSetting.setOnClickListener{
            if (mainViewModel.isAuthorizedMember(PrefManager.read(Constants.PREF_USER_ID, "")!!))
                activity.navigate(SETTING_TO_SETTINGCHANGE)
            else
                showToast("권한이 없습니다")
        }

        binding.btnDeleteGroup.setOnClickListener {
            if (PrefManager.read(Constants.PREF_USER_ID, "")!! != mainViewModel.teamMaster){
                showToast("그룹장만 그룹을 삭제할 수 있습니다")
            }else{
                showDeleteGroupDialog()
            }
        }

        binding.btnExitGroup.setOnClickListener {
            if (PrefManager.read(Constants.PREF_USER_ID, "")!! == mainViewModel.teamMaster)
                showToast("그룹장은 그룹을 나갈 수 없습니다. 그룹장을 다른 그룹원에게 위임하세요.")
            else
                showExitGroupDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showDeleteGroupDialog(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_delete_group, activity.findViewById(R.id.cl_delete_group_dialog)
        )

        val etPw = view.findViewById<TextInputLayout>(R.id.et_pw)
        val btnCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)

        builder.setView(view)
        deleteGroupDialog = builder.create()
        deleteGroupDialog.apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }.show()


        btnCancel.setOnClickListener {
            deleteGroupDialog.dismiss()
        }

        btnSave.setOnClickListener {
            Log.d(TAG, "showDeleteGroupDialog: ${PrefManager.read(Constants.PREF_USER_ID, "")!!}, ${etPw.editText?.text.toString()}")
            viewModel.deleteTeam(PrefManager.read(Constants.PREF_USER_ID, "")!!, etPw.editText?.text.toString(), mainViewModel.teamId)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showExitGroupDialog(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_exit_group, activity.findViewById(R.id.cl_exit_group_dialog)
        )

        val etPw = view.findViewById<TextInputLayout>(R.id.et_pw)
        val btnCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)

        builder.setView(view)
        exitGroupDialog = builder.create()
        exitGroupDialog.apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }.show()


        btnCancel.setOnClickListener {
            exitGroupDialog.dismiss()
        }

        btnSave.setOnClickListener {
            Log.d(TAG, "showDeleteGroupDialog: ${PrefManager.read(Constants.PREF_USER_ID, "")!!}, ${etPw.editText?.text.toString()}")
            viewModel.leaveTeam(PrefManager.read(Constants.PREF_USER_ID, "")!!, etPw.editText?.text.toString(), mainViewModel.teamId)
        }
    }

}