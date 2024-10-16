package com.mlbdev.mantapluarbiasa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.MemberCardBinding

class MemberAdapter(private val memberActivity: Array<MembersBank>): RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {
    class MemberViewHolder(val binding: MemberCardBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding  = MemberCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.binding.txtNamaMember.text = memberActivity[position].name

        holder.binding.imgMember.setImageResource(memberActivity[position].img)

        holder.binding.txtRole.text = "Role : " + memberActivity[position].role
    }

    override fun getItemCount(): Int {
        return memberActivity.size
    }
}