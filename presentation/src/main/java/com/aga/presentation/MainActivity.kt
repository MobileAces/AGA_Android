package com.aga.presentation

import android.os.Bundle
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.databinding.ActivityMainBinding
import com.aga.presentation.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNavigation()
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

        }
    }
}