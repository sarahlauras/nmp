package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.ScheduleCardBinding

class OurScheduleAdapter(): RecyclerView.Adapter<OurScheduleAdapter.OurScheduleViewHolder>() {
    class OurScheduleViewHolder(val binding:ScheduleCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OurScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurScheduleViewHolder, position: Int) {
        holder.binding.txtTgl.text = ourScheduleData.scheduleData[position].date
        holder.binding.txtNamaSchedule.text = ourScheduleData.scheduleData[position].nama_schedule
        holder.binding.txtGame.text = ourScheduleData.scheduleData[position].nama_game
        holder.binding.txtTeam.text = ourScheduleData.scheduleData[position].nama_team

        holder.binding.recSchedule.setOnClickListener {
            val intent = Intent(holder.itemView.context, SchedulePageDetail::class.java)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return ourScheduleData.scheduleData.size
    }

}