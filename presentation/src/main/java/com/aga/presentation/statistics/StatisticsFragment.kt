package com.aga.presentation.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aga.presentation.MainActivity
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentStatisticsBinding
import com.aga.presentation.statistics.BottomSheetStatisticsDaily
import com.aga.presentation.statistics.BottomSheetStatisticsFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(
    FragmentStatisticsBinding::bind, R.layout.fragment_statistics
) {
    private val viewModel: StatisticsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var activity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as MainActivity

        registerObserver()
        registerListener()

    }

    private fun registerListener(){
        binding.ivStatisticsPeriod.setOnClickListener {
            BottomSheetStatisticsFilter().show(activity.supportFragmentManager, "period")
        }

        binding.ivStatisticsDaily.setOnClickListener {
            BottomSheetStatisticsDaily().show(activity.supportFragmentManager, "daily")
        }
    }

    private fun registerObserver(){

    }

}