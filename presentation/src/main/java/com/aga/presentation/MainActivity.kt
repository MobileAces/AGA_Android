package com.aga.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.aga.presentation.alarm.AlarmFragment
import com.aga.presentation.alarm.AlarmSettingFragment
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.base.Constants
import com.aga.presentation.base.Constants.ALARMSETTING_TO_ALARM
import com.aga.presentation.base.Constants.ALARM_TO_ALARMSETTING
import com.aga.presentation.base.Constants.SETTINGCHANGE_TO_SETTING
import com.aga.presentation.base.Constants.SETTING_TO_SETTINGCHANGE
import com.aga.presentation.databinding.ActivityMainBinding
import com.aga.presentation.setting.SettingChangeFragment
import com.aga.presentation.setting.SettingFragment
import com.aga.presentation.util.collapse
import com.aga.presentation.util.expand
import com.aga.presentation.statistics.StatisticsFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity_AWSOME"
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.teamId = intent.getIntExtra(Constants.PREF_GROUP_ID, -1)

        initBottomNavigation()

        viewModel.loadAuthorizedMember(viewModel.teamId)

        viewModel.authorizedMemberList.observe(this){
            it.forEach {member ->
                Log.d(TAG, "onCreate: ${member.userNickname}, ${member.authority}")
            }
        }
    }

    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, AlarmFragment())
            .commitAllowingStateLoss()

        binding.bnvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.alarm -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_main, AlarmFragment())
                        .commitAllowingStateLoss()
                }
                R.id.statistics -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_main, StatisticsFragment())
                        .commitAllowingStateLoss()
                }
                R.id.setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_main, SettingFragment())
                        .commitAllowingStateLoss()
                }
            }
            true
        }
    }

    fun navigate(id: Int){
        when(id){
            SETTING_TO_SETTINGCHANGE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_main, SettingChangeFragment())
                    .addToBackStack("setting_change")
                    .commitAllowingStateLoss()
            }
            SETTINGCHANGE_TO_SETTING -> {
                supportFragmentManager.popBackStack("setting_change", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_main, SettingFragment())
                    .commitAllowingStateLoss()
            }
            ALARM_TO_ALARMSETTING -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_main,AlarmSettingFragment())
                    .commit()
                changeBottomNavVisible(false)
            }
            ALARMSETTING_TO_ALARM -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_main,AlarmFragment())
                    .commit()
                changeBottomNavVisible(true)
            }
        }
    }

    private fun changeBottomNavVisible(state: Boolean){
        if (state){
            binding.bnvMain.expand()
        } else {
            binding.bnvMain.collapse()
        }
    }
}