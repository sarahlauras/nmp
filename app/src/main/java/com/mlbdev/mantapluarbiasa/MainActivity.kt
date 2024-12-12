package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments:ArrayList<Fragment> = arrayListOf(
            WhatWePlayFragment.newInstance(),
            //OurScheduleFragment.newInstance(OurScheduleData.scheduleData),
            WhoWeAreFragment.newInstance()
        )

        binding.viewpager.adapter = HomeAdapter(this, fragments)

        binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() { //dipanggil ketika geser kanan atau kiri fragmentnya
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(position).itemId
            }
        })

        //button nav
        binding.bottomNav.setOnItemSelectedListener {
            if (it.itemId == R.id.itemWhatWePlay) {
                binding.viewpager.currentItem = 0 //it itu refer ke buttomnav
            } else if (it.itemId == R.id.itemSchedule) {
                binding.viewpager.currentItem = 1
            } else if (it.itemId == R.id.itemWhoWeAre) {
                binding.viewpager.currentItem = 2
            }
            true
        }
        binding.btnApplyTeam.setOnClickListener {
            val intent = Intent(this, ApplyTeamActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
        }
    }
    private fun signOut() {
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("USERNAME")
        editor.apply()

        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }

}