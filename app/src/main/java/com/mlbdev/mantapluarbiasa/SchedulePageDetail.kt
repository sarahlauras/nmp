package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivitySchedulePageDetailBinding

class SchedulePageDetail : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulePageDetailBinding
    private lateinit var scheduleDetail: ScheduleDetailBank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchedulePageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleIndex = intent.getIntExtra("SCHEDULE_INDEX", 0)
        //val username = sharedPreferences.getString("USERNAME", null)

//        val location = ScheduleDetailData.scheduleDetails[scheduleIndex].location
//        binding.txtLocation.text = location

//        val time = ScheduleDetailData.scheduleDetails[scheduleIndex].time
//        binding.txtDateTime.text = time

//        val date = OurScheduleData.scheduleData[scheduleIndex].date
//        binding.txtDateTime.text = date
//
//        val description = ScheduleDetailData.scheduleDetails[scheduleIndex].desctipiton
//        binding.txtDescription.text = description
//
//        val img = ScheduleDetailData.scheduleDetails[scheduleIndex].Img
//        binding.imageView5.setImageResource(img)
//
//        val teamName = intent.getStringExtra("TEAM_NAME")
//        binding.txtTeam.text = teamName
//
//        val gameName = intent.getStringExtra("GAME_NAME")
//        binding.txtGame.text = gameName

//        val scheduleName = intent.getStringExtra("SCHEDULE_NAME")
//        binding.txtName.text = scheduleName

        binding.btnNotify.setOnClickListener(){
            Toast.makeText(this,"Notification Created", Toast.LENGTH_SHORT).show()
        }
    }

}