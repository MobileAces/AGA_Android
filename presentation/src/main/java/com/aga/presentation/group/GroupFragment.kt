package com.aga.presentation.group

import android.os.Bundle
import android.view.View
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding>(
    FragmentGroupBinding::bind, R.layout.fragment_group
) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}