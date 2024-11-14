package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.ScheduleCardBinding

class OurScheduleAdapter(private val scheduleList:ArrayList<OurScheduleBank>): RecyclerView.Adapter<OurScheduleAdapter.OurScheduleViewHolder>() {
    class OurScheduleViewHolder(val binding:ScheduleCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OurScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]

        holder.binding.txtTgl.text = schedule.date
        holder.binding.txtNamaSchedule.text = schedule.nama_schedule
        holder.binding.txtGame.text = schedule.nama_game
        holder.binding.txtTeam.text = schedule.nama_team

        holder.binding.recSchedule.setOnClickListener {
            val intent = Intent(holder.itemView.context, SchedulePageDetail::class.java)
            intent.putExtra("SCHEDULE_INDEX", position)
            intent.putExtra("TEAM_NAME", schedule.nama_team)
            intent.putExtra("GAME_NAME", schedule.nama_game)
            intent.putExtra("SCHEDULE_NAME", schedule.nama_schedule)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

}