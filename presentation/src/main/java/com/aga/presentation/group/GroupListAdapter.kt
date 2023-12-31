package com.aga.presentation.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnGroupClickListener
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamWithMember
import com.aga.presentation.R
import com.aga.presentation.databinding.ItemMyGroupBinding

class GroupListAdapter(
    private var groupList: List<TeamWithMember>,
    private var groupClickListener: (TeamWithMember) -> Unit
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
        holder.bind(groupList[position],groupClickListener)
    }

    fun changeDataSet(groupList: List<TeamWithMember>){
        this.groupList = groupList
        notifyDataSetChanged()
    }

    class GroupListViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var memberListAdapter: GroupMemberListAdapter? = null

        fun bind(teamWithMember: TeamWithMember,groupClickListener: (TeamWithMember) -> Unit) {
            binding.tvGroupName.text = teamWithMember.teamName
            binding.tvGroupIntroduction.text = teamWithMember.teamInfo
            binding.tvGroupOwner.text = teamWithMember.teamMaster
            setShowMemberButton()
            setMemberListAdapter(teamWithMember.memberList)
            // 카드 클릭 리스너, 액티비티 전환코드를 프래그먼트에서 작성
            binding.cvGroup.setOnClickListener {
                groupClickListener(teamWithMember)
            }
        }

        private fun setMemberListAdapter(memberList: List<TeamMember>){
            if (memberListAdapter == null){
                memberListAdapter = GroupMemberListAdapter(memberList)
            }
            binding.rvMember.adapter = memberListAdapter
        }

        private fun setShowMemberButton(){
            binding.ivShowMember.setOnClickListener {
                if (binding.rvMember.visibility == View.GONE) {
                    binding.rvMember.visibility = View.VISIBLE
                    binding.ivShowMember.setImageResource(
                        R.drawable.ic_arrow_up
                    )
                } else if (binding.rvMember.visibility == View.VISIBLE) {
                    binding.rvMember.visibility = View.GONE
                    binding.ivShowMember.setImageResource(
                        R.drawable.ic_arrow_down
                    )
                }
            }
        }
    }
}