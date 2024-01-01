package com.aga.presentation

import android.os.Bundle
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.ActivityGroupBinding
import com.aga.presentation.group.GroupFragment
import com.aga.presentation.login.JoinOneFragment
import com.aga.presentation.profile.ProfileChangeFragment
import com.aga.presentation.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupActivity : BaseActivity<ActivityGroupBinding>(
    ActivityGroupBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_group, GroupFragment())
            .commit()
    }

    fun navigate(id: Int){
        when(id) {
            Constants.GROUP_TO_PROFILE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_group, ProfileFragment())
                    .addToBackStack("profile")
                    .commitAllowingStateLoss()
            }
            Constants.PROFILE_TO_PROFILECHANGE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_group, ProfileChangeFragment())
                    .addToBackStack("profile_change")
                    .commitAllowingStateLoss()
            }
            Constants.PROFILECHANGE_TO_PROFILE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_group, ProfileFragment())
                    .commitAllowingStateLoss()
            }
        }
    }
}