package com.aga.presentation.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamWithMember
import com.aga.presentation.databinding.ItemMyGroupBinding

class GroupListAdapter(
    private var groupList: List<TeamWithMember>
) : RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        return GroupListViewHolder(
            ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        holder.bind(groupList[position])
    }

    class GroupListViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(teamWithMember: TeamWithMember) {
            binding.tvGroupName.text = teamWithMember.teamName
            binding.tvGroupIntroduction.text = teamWithMember.teamInfo
            binding.tvGroupOwner.text = teamWithMember.teamMaster
            binding.ivShowMember.setOnClickListener {
                if (binding.rvMember.visibility == View.GONE) {
                    binding.rvMember.visibility = View.VISIBLE
                } else if (binding.rvMember.visibility == View.VISIBLE) {
                    binding.rvMember.visibility = View.GONE
                }
            }
            setMemberListAdapter(teamWithMember.memberList)
        }

        private fun setMemberListAdapter(memberList: List<TeamMember>){
            if (binding.rvMember.adapter == null){
                binding.rvMember.adapter = GroupMemberListAdapter(memberList)
            }
        }
    }
}