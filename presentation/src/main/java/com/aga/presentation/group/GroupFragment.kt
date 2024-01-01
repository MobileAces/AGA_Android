package com.aga.presentation.group

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.aga.domain.model.TeamWithMember
import com.aga.presentation.GroupActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.FragmentGroupBinding
import com.leinardi.android.speeddial.SpeedDialActionItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::bind, R.layout.fragment_group
) {
    private lateinit var activity: GroupActivity

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

    }

    private fun registerObserve() {
        groupViewModel.groupList.observe(viewLifecycleOwner) {
            if (groupListAdapter == null) {
                groupListAdapter = GroupListAdapter(it, groupClickListener)
                binding.rvGroup.adapter = groupListAdapter
            } else {
                groupListAdapter?.changeDataSet(it)
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

                }
                R.id.fab_create_croup -> {
                    activity.navigate(Constants.GROUP_TO_CREATEGROUP)
                }
            }
            return@setOnActionSelectedListener true
        }
    }
}