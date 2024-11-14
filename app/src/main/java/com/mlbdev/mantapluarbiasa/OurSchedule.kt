package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.mantapluarbiasa.databinding.ActivityOurScheduleBinding


class OurSchedule : AppCompatActivity() {
    private lateinit var binding:ActivityOurScheduleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOurScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.schedulePage.layoutManager = LinearLayoutManager(this)
        binding.schedulePage.setHasFixedSize(true)

        val scheduleList = OurScheduleData.scheduleData
        binding.schedulePage.adapter = OurScheduleAdapter(scheduleList)
    }

}
