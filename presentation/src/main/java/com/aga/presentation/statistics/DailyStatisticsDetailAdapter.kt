package com.aga.presentation.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.DailyStatisticsDetail
import com.aga.presentation.R
import com.aga.presentation.databinding.ItemStatisticsDailyDetailBinding
import com.aga.presentation.util.collapse
import com.aga.presentation.util.expand

class DailyStatisticsDetailAdapter(
    private var itemList: List<DailyStatisticsDetail>
):RecyclerView.Adapter<DailyStatisticsDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStatisticsDailyDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: DailyStatisticsDetail){
            binding.llDetail.visibility = View.GONE

            binding.tvName.text = item.userNickname
            binding.tvAmpm.text = if (item.wakeupHour < 12) "오전" else "오후"
            val hour = if (item.wakeupHour < 10) "0${item.wakeupHour}" else item.wakeupHour.toString()
            val min = if (item.wakeupMinute < 10) "0${item.wakeupMinute}" else item.wakeupMinute.toString()
            binding.tvTime.text = "$hour:$min"
            if (item.success)
                binding.ivStatusSuccess.visibility = View.VISIBLE
            else
                binding.ivStatusFail.visibility = View.VISIBLE
            binding.tvMemo.text = if (item.wakeupVoice) "ON" else "OFF"
            binding.tvWeather.text = if (item.wakeupForecast) "ON" else "OFF"
            binding.tvMemoContent.text = item.wakeupMemo ?: "설정된 메모가 없습니다."

            binding.ivShowMore.setOnClickListener {
                if (binding.llDetail.visibility == View.GONE){
                    binding.llDetail.expand()
                    binding.ivShowMore.setImageResource(R.drawable.ic_arrow_up)
                }else {
                    binding.llDetail.collapse()
                    binding.ivShowMore.setImageResource(R.drawable.ic_arrow_down)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatisticsDailyDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}