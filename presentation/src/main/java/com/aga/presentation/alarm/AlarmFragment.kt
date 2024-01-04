package com.aga.presentation.alarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentAlarmBinding
import com.aga.presentation.databinding.FragmentProfileBinding

class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::bind, R.layout.fragment_alarm
) {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
        registerListener()
        registerObserve()
    }

    private fun registerListener(){
        binding.ibAddNewAlarm.setOnClickListener { 
            AlarmCreateDialog(requireContext())
                .setCancelListener {dialog ->  
                    dialog.dismiss()
                }
                .setCreateListener { dialog, selectedDay, alarmname ->

                }
                .show()
        }
    }

    private fun registerObserve(){

    }
    
}