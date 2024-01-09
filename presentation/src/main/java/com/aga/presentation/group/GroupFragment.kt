package com.aga.presentation.group

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.aga.domain.model.TeamWithMember
import com.aga.presentation.GroupActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentGroupBinding
import com.google.android.material.textfield.TextInputLayout
import com.leinardi.android.speeddial.SpeedDialActionItem
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GroupFragment_AWSOME"
@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::bind, R.layout.fragment_group
) {
    private lateinit var activity: GroupActivity
    private lateinit var joinGroupDialog: AlertDialog

    private val groupViewModel: GroupViewModel by viewModels()
    private var groupListAdapter: GroupListAdapter? = null

    private val groupClickListener: (TeamWithMember) -> Unit = {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            putExtra(Constants.PREF_GROUP_ID, it.teamId)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = requireActivity() as GroupActivity

        setFabSpeedDialUi()
        registerListener()
        registerObserve()

        groupViewModel.getGroupList()

    }

    private fun registerListener() {
        binding.ivProfile.setOnClickListener {
            activity.navigate(Constants.GROUP_TO_PROFILE)
        }
    }


    private fun registerObserve() {
        groupViewModel.groupList.observe(viewLifecycleOwner) {
            if (binding.rvGroup.adapter == null){
                if (groupListAdapter == null){
                    groupListAdapter = GroupListAdapter(it, groupClickListener)
                }
                binding.rvGroup.adapter = groupListAdapter
            } else {
                groupListAdapter?.changeDataSet(it)
            }
        }

        groupViewModel.registerTeamResult.observe(viewLifecycleOwner){
            if (it){
                showToast("그룹에 가입되었습니다.")
                groupViewModel.getGroupList()
                joinGroupDialog.dismiss()
            }
        }
    }

    private fun setFabSpeedDialUi() {
        binding.fabAddGroup.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_create_croup, R.drawable.ic_grouplist_create_group
            )
                .setLabel(getString(R.string.group_create))
                .create()
        )
        binding.fabAddGroup.addActionItem(
            SpeedDialActionItem.Builder(
                R.id.fab_join_croup, R.drawable.ic_grouplist_join_group
            )
                .setLabel(getString(R.string.group_join))
                .create()
        )

        binding.fabAddGroup.setOnActionSelectedListener { item ->
            when (item.id) {
                R.id.fab_join_croup -> {
                    showJoinGroupDialog()
                }
                R.id.fab_create_croup -> {
                    binding.fabAddGroup.close()
                    activity.navigate(Constants.GROUP_TO_CREATEGROUP)
                }
            }
            return@setOnActionSelectedListener true
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showJoinGroupDialog(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_join_group, activity.findViewById(R.id.cl_join_group_dialog)
        )

        val etCode = view.findViewById<TextInputLayout>(R.id.et_code)
        val btnCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val btnSave = view.findViewById<TextView>(R.id.tv_save)

        builder.setView(view)
        joinGroupDialog = builder.create()
        joinGroupDialog.apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }.show()


        btnCancel.setOnClickListener {
            joinGroupDialog.dismiss()
        }

        btnSave.setOnClickListener {
            groupViewModel.confirmInviteCode(etCode.editText?.text.toString())
        }
    }
}