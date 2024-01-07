package com.aga.presentation.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.DailyStatisticsDetail
import com.aga.domain.model.PeriodStatisticsUser
import com.aga.presentation.MainActivity
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentStatisticsBinding
import com.aga.presentation.statistics.BottomSheetStatisticsDaily
import com.aga.presentation.statistics.BottomSheetStatisticsFilter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "StatisticsFragment_AWSOME"
@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(
    FragmentStatisticsBinding::bind, R.layout.fragment_statistics
) {
    private val viewModel: StatisticsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var periodAdapter: PeriodStatisticsAdapter
    private lateinit var dailyAdapter: DailyStatisticsAdapter
    private var periodItems = arrayListOf<PeriodStatisticsUser>()
    private var dailyItems = arrayListOf<DailyStatistics>()

    private lateinit var activity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = _activity as MainActivity

        periodAdapter = PeriodStatisticsAdapter(periodItems)
        dailyAdapter = DailyStatisticsAdapter(dailyItems, activity)

        binding.rvStatisticsPeriod.apply {
            adapter = periodAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        binding.rvStatisticsDaily.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(activity)
        }

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

    @SuppressLint("NotifyDataSetChanged")
    private fun registerObserver(){
        mainViewModel.periodStatisticsRequest.observe(viewLifecycleOwner){
            if (it){
                viewModel.getPeriodStatistics(mainViewModel.periodStatisticsSelectedMemberList,
                    mainViewModel.teamId,
                    mainViewModel.periodStatisticsStartDate,
                    mainViewModel.periodStatisticsEndDate)
            }
        }

        viewModel.periodStatisticsResult.observe(viewLifecycleOwner){
            periodItems.clear()
            periodItems.addAll(it.userList)
            binding.rvStatisticsPeriod.adapter!!.notifyDataSetChanged()
            binding.llPeriodStatisticsTotal.visibility = View.VISIBLE
            binding.tvStatisticsPeriodTotal.text = "${it.totalSuccessSum}/${it.totalSum}"
            binding.pbStatisticsTotal.progress = it.totalSuccessSum
            binding.pbStatisticsTotal.max = it.totalSum
            Log.d(TAG, "registerObserver: ${it.totalSuccessSum}/${it.totalSum}")

            mainViewModel.periodStatisticsRequest.value = false
        }

        mainViewModel.dailyStatisticsRequest.observe(viewLifecycleOwner){
            if (it){
                viewModel.getDailyStatistics(mainViewModel.teamId, mainViewModel.dailyStatisticsDate)
            }
        }

        viewModel.dailyStatisticsResult.observe(viewLifecycleOwner){
            dailyItems.clear()
            dailyItems.addAll(it)
            binding.rvStatisticsDaily.adapter!!.notifyDataSetChanged()

            mainViewModel.dailyStatisticsRequest.value = false
        }

        viewModel.toastMsg.observe(viewLifecycleOwner){
            showToast(it)
        }
    }

}