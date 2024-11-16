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

        // Cek session saat membuka halaman ini
        if (!checkSession()) {
            // Jika belum login, arahkan ke halaman login
            startActivity(Intent(this, SignIn::class.java))
            finish() // Tutup halaman ini agar tidak bisa kembali
        }


        val fragments:ArrayList<Fragment> = ArrayList()
        fragments.add(WhatWePlayFragment())
        fragments.add(OurScheduleFragment.newInstance(OurScheduleData.scheduleData))
        fragments.add(WhoWeAreFragment.newInstance(gameIndex = 0))

        binding.viewpager.adapter = HomeAdapter(this, fragments)

        binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() { //diapnggil ketika geser kanan atau kiri fragmentnya
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(position).itemId
            }
        })

        //button nav
        binding.bottomNav.setOnItemSelectedListener {
            if(it.itemId == R.id.itemWhatWePlay) {
                binding.viewpager.currentItem = 0 //it itu refer ke buttomnav
            }
            else if(it.itemId == R.id.itemSchedule) {
                binding.viewpager.currentItem = 1
            }
            else if(it.itemId == R.id.itemWhoWeAre) {
                binding.viewpager.currentItem = 2
            }
            true
        }
    }

    private fun checkSession(): Boolean {
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        return sharedPreferences.getBoolean("IsLoggedIn", false) // Ambil status login
    }

}