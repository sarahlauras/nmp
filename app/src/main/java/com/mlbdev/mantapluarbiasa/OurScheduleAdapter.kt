package com.mlbdev.mantapluarbiasa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.ScheduleCardBinding

class OurScheduleAdapter(): RecyclerView.Adapter<OurScheduleAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding:ScheduleCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.txtTgl.text = ourScheduleData.scheduleData[position].date
        holder.binding.txtNamaSchedule.text = ourScheduleData.scheduleData[position].nama_schedule
        holder.binding.txtGame.text = ourScheduleData.scheduleData[position].nama_game
        holder.binding.txtTeam.text = ourScheduleData.scheduleData[position].nama_team
    }

    override fun getItemCount(): Int {
        return ourScheduleData.scheduleData.size
    }

}