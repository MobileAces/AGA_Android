package com.aga.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aga.domain.model.AlarmDetail
import com.aga.domain.usecase.alarmdetail.GetAllLocalAlarmDetailByUserId
import com.aga.presentation.base.AgaAlarmManager
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RebootReceiver @Inject constructor(
    private val getAllLocalAlarmDetailByUserId: GetAllLocalAlarmDetailByUserId
) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == "android.intent.action.BOOT_COMPLETED"){
            val userId = PrefManager.read(Constants.PREF_USER_ID, "")
            CoroutineScope(Dispatchers.IO).launch {
                getAllLocalAlarmDetailByUserId.invoke(userId!!).filter {
                    it.isOn
                }.forEach {
                    AgaAlarmManager.setNewAlarm(it, p0!!)
                }
            }
        }
    }

}