package com.aga.presentation

import android.os.Bundle
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.ActivityGroupBinding
import com.aga.presentation.group.GroupCreateFragment
import com.aga.presentation.group.GroupFragment
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

    fun navigate(id: Int) {
        when (id) {
            Constants.GROUP_TO_CREATEGROUP -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_group,GroupCreateFragment())
                    .commit()
            }
            Constants.TO_GROUP -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_group,GroupFragment())
                    .commit()
            }
        }
    }
}