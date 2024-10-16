package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.GameAdapter.GameViewHolder
import com.mlbdev.mantapluarbiasa.databinding.TeamsCardBinding

class TeamAdapter(private val teams: Array<TeamBank>, private val gameIndex: Int): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    class TeamViewHolder(val binding: TeamsCardBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding  = TeamsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.binding.txtTeamName.text = teams[position].name

        holder.binding.recTeams.setOnClickListener {
            val intent = Intent(holder.itemView.context, MemberActivity::class.java)
            intent.putExtra("GAME_INDEX", gameIndex)
            intent.putExtra("TEAM_INDEX", teams[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return teams.size
    }
}