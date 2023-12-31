package com.aga.presentation.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.aga.domain.model.TeamWithMember
import com.aga.presentation.GroupActivity
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.FragmentGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::bind, R.layout.fragment_group
) {
    private val groupViewModel: GroupViewModel by viewModels()
    private var groupListAdapter: GroupListAdapter? = null
    private lateinit var activity: GroupActivity

    private val groupClickListener: (TeamWithMember) -> Unit = {
        startActivity(Intent(requireContext(),MainActivity::class.java).apply {
            putExtra(Constants.PREF_GROUP_ID,it.teamId)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as GroupActivity

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
        groupViewModel.groupList.observe(viewLifecycleOwner){
            if (groupListAdapter == null){
                groupListAdapter = GroupListAdapter(it,groupClickListener)
                binding.rvGroup.adapter = groupListAdapter
            } else {
                groupListAdapter?.changeDataSet(it)
            }
        }
    }
}