package com.aga.presentation.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.TeamMember
import com.aga.presentation.databinding.ItemSettingMemberBinding

class TeamMemberAdapter(private val itemList: ArrayList<TeamMember>):
RecyclerView.Adapter<TeamMemberAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemSettingMemberBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TeamMember){
            binding.tvMemberName.setText(item.userNickname)
            if (item.authority == 2)
                binding.ivCrown.visibility = View.VISIBLE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): TeamMemberAdapter.ViewHolder {
        val binding = ItemSettingMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamMemberAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}