package com.aga.presentation.group

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::bind, R.layout.fragment_group
) {
    private val groupViewModel: GroupViewModel by viewModels()
    private var groupListAdapter: GroupListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerListener()
        registerObserve()

        groupViewModel.getGroupList()
    }

    private fun registerListener() {

    }

    private fun registerObserve() {
        groupViewModel.groupList.observe(viewLifecycleOwner){
            if (groupListAdapter == null){
                groupListAdapter = GroupListAdapter(it)
                binding.rvGroup.adapter = groupListAdapter
            } else {
                groupListAdapter?.changeDataSet(it)
            }
        }
    }
}