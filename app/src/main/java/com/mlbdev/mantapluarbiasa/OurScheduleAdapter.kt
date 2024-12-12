package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.ScheduleCardBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OurScheduleAdapter(private val scheduleList:ArrayList<OurScheduleBank>): RecyclerView.Adapter<OurScheduleAdapter.OurScheduleViewHolder>() {
    class OurScheduleViewHolder(val binding:ScheduleCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OurScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]

        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd")  // Format yang sesuai dengan format date string yang diberikan
        val myDate = dateFormatInput.parse(schedule.date)  // Mengonversi string ke Date

        // Format objek Date ke format yang diinginkan
        val dateFormatOutput = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))  // Format yang diinginkan untuk ditampilkan
        val formattedDate = dateFormatOutput.format(myDate)

        holder.binding.txtTgl.text = formattedDate
        holder.binding.txtNamaSchedule.text = schedule.nama_schedule
        holder.binding.txtGame.text = schedule.nama_game
        holder.binding.txtTeam.text = schedule.nama_team

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, SchedulePageDetail::class.java).apply {
                putExtra("SCHEDULE_NAME", schedule.nama_schedule)
                putExtra("GAME_NAME", schedule.nama_game)
                putExtra("TEAM_NAME", schedule.nama_team)
                putExtra("SCHEDULE_DATE", schedule.date)
                putExtra("SCHEDULE_INDEX", position)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

}