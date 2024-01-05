package com.aga.presentation.alarm

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.aga.presentation.databinding.DialogAlarmCreateBinding
import java.time.DayOfWeek

class AlarmCreateDialog(private val context: Context) : Dialog(context) {

    private var binding: DialogAlarmCreateBinding = DialogAlarmCreateBinding.inflate(LayoutInflater.from(context))
    private var cancelListener: (dialog: Dialog) -> Unit = { _ -> }
    private var createListener: (dialog: Dialog, selectedDay: Set<DayOfWeek>, alarmname: String) -> Unit = { _, _, _ -> }

    private lateinit var weekDaySelectManager: WeekDaySelectManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 다이얼로그의 기본 배경색을 투명하게
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        weekDaySelectManager = WeekDaySelectManager(binding.layoutSelectWeek)

        applyCreateListener()
        applyCancleListener()
    }

    private fun applyCancleListener() {
        binding.tvCancel.setOnClickListener {
            cancelListener(this)
        }
    }

    private fun applyCreateListener() {
        binding.tvCreate.setOnClickListener {
            createListener(this,weekDaySelectManager.getSelectedDay(),binding.tilNewAlarmTitle.editText!!.text.toString())
        }
    }

    fun setCancelListener(cancelListener: (dialog: Dialog) -> Unit): AlarmCreateDialog{
        this.cancelListener = cancelListener
        applyCancleListener()
        return this
    }

    fun setCreateListener(createListener: (dialog: Dialog, selectedDay: Set<DayOfWeek>, alarmname: String) -> Unit): AlarmCreateDialog{
        this.createListener = createListener
        applyCreateListener()
        return this
    }
}