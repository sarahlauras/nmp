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

        // Populate Spinner with years
        val years = mutableListOf("All") + AchievementData.achievement.map { it.year }.distinct()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        binding.spinnerYear.adapter = adapter

        // Set default selection to "All"
        binding.spinnerYear.setSelection(0)

        // Set listener for Spinner
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterAchievements()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun filterAchievements() {
        val selectedYear = binding.spinnerYear.selectedItem.toString()
        val gameName = binding.txtGame.text.toString().trim()

        val filteredAchievements = AchievementData.achievement.filter {
            (selectedYear == "All" || it.year == selectedYear) &&
                    (it.game.equals(gameName, ignoreCase = true))
        }

        binding.txtAchievement.text = filteredAchievements.joinToString("\n") { it.achievement }
    }


}