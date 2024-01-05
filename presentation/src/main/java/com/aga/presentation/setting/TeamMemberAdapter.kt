package com.aga.presentation.setting

import android.app.Activity
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.TeamMember
import com.aga.presentation.R
import com.aga.presentation.databinding.ItemSettingMemberBinding

private const val TAG = "TeamMemberAdapter_AWSOME"
class TeamMemberAdapter(private val itemList: ArrayList<TeamMember>, private val myAuthority: Int):
RecyclerView.Adapter<TeamMemberAdapter.ViewHolder>(){

    interface ContextMenuClickListener{
        fun onExpelClicked(userId: String)
        fun onMasterClicked(userId: String)
        fun onAuthorityClicked(userId: String, authorize: Boolean)
    }

    private lateinit var contextMenuClickListener: ContextMenuClickListener

    fun setContextMenuClickListener(listener: ContextMenuClickListener){
        this.contextMenuClickListener = listener
    }

    inner class ViewHolder(val binding: ItemSettingMemberBinding): RecyclerView.ViewHolder(binding.root), OnCreateContextMenuListener{
        init {
            if (myAuthority == 2)
                binding.cvMember.setOnCreateContextMenuListener(this)
        }
        fun bind(item: TeamMember){
            binding.tvMemberName.setText(item.userNickname)
            if (item.authority == 2) {
                binding.ivCrown.visibility = View.VISIBLE
            }
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val member = itemList[bindingAdapterPosition]
            if (member.authority != 2){
                var expelMenu = menu?.add(0, 0, 0, "추방")
                expelMenu?.setOnMenuItemClickListener {
                    contextMenuClickListener.onExpelClicked(member.userId)
                    true
                }
                var masterMenu = menu?.add(0, 2, 2, "그룹장 위임")
                masterMenu?.setOnMenuItemClickListener {
                    contextMenuClickListener.onMasterClicked(member.userId)
                    true
                }
                if (member.authority == 1) {
                    var authorityMenu = menu?.add(0, 1, 1, "권한 뺏기")
                    authorityMenu?.setOnMenuItemClickListener {
                        contextMenuClickListener.onAuthorityClicked(member.userId, false)
                        true
                    }
                }else{
                    var authorityMenu = menu?.add(0, 1, 1, "권한 부여")
                    authorityMenu?.setOnMenuItemClickListener {
                        contextMenuClickListener.onAuthorityClicked(member.userId, true)
                        true
                    }
                }
            }
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