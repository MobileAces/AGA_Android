package com.aga.presentation.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aga.domain.model.TeamMember
import com.aga.presentation.databinding.ItemMyGroupMemberBinding

class GroupMemberListAdapter(
    private var memberList: List<TeamMember>
) : Adapter<GroupMemberListAdapter.GroupMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        return GroupMemberViewHolder(
            ItemMyGroupMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        holder.bind(memberList[position])
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    class GroupMemberViewHolder(private val binding: ItemMyGroupMemberBinding) :
        ViewHolder(binding.root) {
        fun bind(teamMember: TeamMember) {
            binding.tvMemberName.text = teamMember.userNickname
        }
    }
}