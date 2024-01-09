package com.aga.presentation.statistics

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.DailyStatisticsDetail
import com.aga.presentation.databinding.ItemStatisticsDailyBinding

private const val TAG = "DailyStatisticsAdapter_AWSOME"
class DailyStatisticsAdapter(
    private var itemList: List<DailyStatistics>,
    private val mContext: Context
): RecyclerView.Adapter<DailyStatisticsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStatisticsDailyBinding): RecyclerView.ViewHolder(binding.root){
        private var detailAdapter: DailyStatisticsDetailAdapter? = null
        fun bind(item: DailyStatistics){
            binding.tvAlarmName.text = item.alarmName
            setDetailAdapter(item.wakeupList)
        }

        private fun setDetailAdapter(detailList: List<DailyStatisticsDetail>){
            if (detailAdapter == null){
                detailAdapter = DailyStatisticsDetailAdapter(detailList)
            }
            Log.d(TAG, "setDetailAdapter: ${detailList.size}")
            binding.rvAlarmDetail.apply {
                adapter = detailAdapter
                layoutManager = LinearLayoutManager(mContext)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatisticsDailyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}