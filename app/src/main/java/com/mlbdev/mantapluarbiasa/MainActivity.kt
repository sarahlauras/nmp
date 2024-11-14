package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments:ArrayList<Fragment> = ArrayList()
//        OurScheduleFragmentList.newInstance(OurScheduleData.scheduleData)
//        fragments.add(OurScheduleFragmentList())
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

}