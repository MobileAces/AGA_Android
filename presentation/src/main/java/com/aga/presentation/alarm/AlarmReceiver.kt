package com.aga.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aga.domain.usecase.alarmdetail.CheckNotDeletedAlarmUseCase
import com.aga.domain.usecase.alarmdetail.DeleteAlarmDetailFromLocalUseCase
import com.aga.domain.usecase.alarmdetail.GetLocalAlarmDetailUseCase
import com.aga.presentation.base.AgaAlarmManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver @Inject constructor(
    private val getLocalAlarmDetailUseCase: GetLocalAlarmDetailUseCase,
    private val checkNotDeletedAlarmUseCase: CheckNotDeletedAlarmUseCase,
    private val deleteAlarmDetailFromLocal: DeleteAlarmDetailFromLocalUseCase
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("alarm_detail_id", -1)!!
        val isRepeat = intent.getBooleanExtra("isRepeatAlarm", false)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (checkNotDeletedAlarmUseCase.invoke(id)){
                    getAlarmDetail(id, isRepeat, context)
                }else{
                    //삭제된 알람이라는 뜻
                    removeAlarmInLocal(id, context)
                }
            }catch (e: Exception){
                getAlarmDetail(id, isRepeat, context)
            }
        }
    }

    private fun getAlarmDetail(alarmDetailId: Int, isRepeat: Boolean,  context: Context?){
        //Room 에서 알람 디테일 정보 가져오기
        CoroutineScope(Dispatchers.IO).launch {
            val alarmDetail = getLocalAlarmDetailUseCase.invoke(alarmDetailId)
            val serviceIntent = Intent(context, AlarmService::class.java)
            serviceIntent.apply {
                putExtra("alarm_detail", alarmDetail)
                putExtra("isRepeatAlarm", isRepeat)
            }

            context!!.startForegroundService(serviceIntent)
        }
    }

    private fun removeAlarmInLocal(alarmDetailId: Int,  context: Context?){
        CoroutineScope(Dispatchers.IO).launch {
            val alarmDetail = getLocalAlarmDetailUseCase.invoke(alarmDetailId)
            AgaAlarmManager.cancelAlarm(alarmDetail, context!!)
            deleteAlarmDetailFromLocal.invoke(alarmDetailId)
        }
    }
}