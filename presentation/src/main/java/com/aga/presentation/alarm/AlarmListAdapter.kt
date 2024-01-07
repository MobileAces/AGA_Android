package com.aga.presentation.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.AlarmWithDetailList
import com.aga.domain.model.TeamMember
import com.aga.presentation.databinding.ItemAlarmListBinding

class AlarmListAdapter(
    private var alarmList: List<AlarmWithDetailList>,
    private val mamberList: List<TeamMember>
) : RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListViewHolder {
        return AlarmListViewHolder(
            ItemAlarmListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlarmListViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    class AlarmListViewHolder(val binding: ItemAlarmListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlarmWithDetailList) {

        }
    }
}