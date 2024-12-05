package com.mlbdev.mantapluarbiasa

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivityAchievementBinding

class Achievement : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val years = mutableListOf("All") + AchievementData.achievement.map { it.year }.distinct() //map itu buat semacam list baru, distinct itu cmn nampiilin sekali data yg >1
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        binding.spinnerYear.adapter = adapter

        val gameIndex = intent.getIntExtra("GAME_INDEX",0)

        val gameName = GameData.gameData[gameIndex].name
        binding.txtGame.text = gameName

        val gameImg = GameData.gameData[gameIndex].imageId
        binding.imgPreview.setImageResource(gameImg)

        updateYearSpinner(gameIndex) //update sesuai gameIndex

        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterAchievements() //filter sesuai tahun yang dipilih
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun updateYearSpinner(gameIndex:Int){
        val years = mutableListOf("All") + AchievementData.achievement
            .filter { it.gamesIndex == gameIndex }
            .map { it.year }
            .distinct()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        binding.spinnerYear.adapter = adapter
        binding.spinnerYear.setSelection(0)
    }

    private fun filterAchievements() {
        val selectedYear = binding.spinnerYear.selectedItem.toString()

        val gameIndex = intent.getIntExtra("GAME_INDEX",0)

        val filteredAchievements = AchievementData.achievement.filter {
            (selectedYear == "All" || it.year == selectedYear) &&
                    (it.gamesIndex== gameIndex)
        }

        val formattedAchievements = filteredAchievements
            .sortedBy { it.year }
            .mapIndexed { index, achievement ->
                "${index + 1}. ${achievement.achievement} (${achievement.year}) - ${achievement.team}"
            }// index+1 = nomor 1,2,3,dst. map indexed = buat list menjadi string dengan format
            .joinToString("\n")

        binding.txtAchievement.text = formattedAchievements
    }
}