package com.aga.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.aga.presentation.databinding.FragmentAlarmSettingBinding

class AlarmSettingFragment : Fragment() {

    private lateinit var binding: FragmentAlarmSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmSettingBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNumberpicker()
    }

    private fun setNumberpicker(){
        binding.npAmpm.apply {
            minValue = 0
            maxValue = 1
            displayedValues = arrayOf("오전","오후")
        }
        binding.npHour.apply {
            minValue = 1
            maxValue = 12
            wrapSelectorWheel = true
            setOnValueChangedListener { picker, oldVal, newVal ->
                // 11시에서 12시로 넘어가거나 그 반대의 경우에 '오전/오후' 상태를 변경
                if ((oldVal == 11 && newVal == 12) || (oldVal == 12 && newVal == 11)) {
                    binding.npAmpm.value = if (binding.npAmpm.value == 0) 1 else 0
                }
            }
        }
        binding.npMinute.apply {
            minValue = 0
            maxValue = 59
            displayedValues = (0..59).map { it.toString().let { if (it.length == 1){"0"+it} else it } }.toTypedArray()
            wrapSelectorWheel = true
        }
    }

}