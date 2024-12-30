package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding
import com.mlbdev.mantapluarbiasa.databinding.DrawerLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: DrawerLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.Main.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        var drawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.Main.toolbar, R.string.app_name, R.string.app_name)

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()



        // Ambil idmember dari SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)
        val idMember = sharedPreferences.getInt("ID_MEMBER", -1)
        val fname = sharedPreferences.getString("FNAME", null)
        val lname = sharedPreferences.getString("LNAME", null)

        val navigationView = binding.navView
        val headerView = navigationView.getHeaderView(0) // Access header view
        val txtUsername = headerView.findViewById<TextView>(R.id.txtUsername) // Access TextView

        txtUsername.text = "Welcome $fname $lname"

        if (username != null) {
            Log.d("MainActivity", "Username: $username")  // Log untuk mengecek username
        } else {
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        val fragments: ArrayList<Fragment> = arrayListOf(
            WhatWePlayFragment.newInstance(),
            OurScheduleFragment.newInstance(),
            WhoWeAreFragment.newInstance()
        )

        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.itemApplyTeam -> {
                    // Gunakan idMember di sini jika perlu untuk apply ke tim
                    val intent = Intent(this, ApplyTeamActivity::class.java)
                    intent.putExtra("ID_MEMBER", idMember) // Pass idMember ke activity selanjutnya
                    startActivity(intent)
                }
                //Snackbar.make(this,binding.root, "Apply Team", Snackbar.LENGTH_SHORT).show()
                R.id.itemProfile -> {
                    //val intentProfile = Intent
                }
                //Snackbar.make(this,binding.root, "Profile", Snackbar.LENGTH_SHORT).show()
                R.id.itemSignOut -> {
                    signOut()
                }
                //Snackbar.make(this,binding.root, "Sign Out", Snackbar.LENGTH_SHORT).show()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.Main.viewpager.adapter = HomeAdapter(this, fragments)

        binding.Main.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.Main.bottomNav.selectedItemId = binding.Main.bottomNav.menu.getItem(position).itemId
            }
        })

        // button nav
        binding.Main.bottomNav.setOnItemSelectedListener {
            if (it.itemId == R.id.itemWhatWePlay) {
                binding.Main.viewpager.currentItem = 0
            } else if (it.itemId == R.id.itemSchedule) {
                binding.Main.viewpager.currentItem = 1
            } else if (it.itemId == R.id.itemWhoWeAre) {
                binding.Main.viewpager.currentItem = 2
            }
            true
        }
    }

    private fun signOut() {
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("USERNAME")
        editor.remove("ID_MEMBER")
        editor.apply()

        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }
}
