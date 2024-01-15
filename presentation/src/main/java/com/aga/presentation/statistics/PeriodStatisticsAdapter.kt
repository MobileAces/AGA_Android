package com.aga.presentation.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.PeriodStatisticsUser
import com.aga.presentation.databinding.ItemStatisticsPeriodBinding

class PeriodStatisticsAdapter(
    private var itemList: ArrayList<PeriodStatisticsUser>
): RecyclerView.Adapter<PeriodStatisticsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStatisticsPeriodBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: PeriodStatisticsUser){
            binding.tvName.text = item.nickname
            binding.pbStatistics.max = item.totalSum
            binding.pbStatistics.progress = item.totalSuccessSum
            binding.tvStatistics.text = "${item.totalSuccessSum}/${item.totalSum}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatisticsPeriodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}