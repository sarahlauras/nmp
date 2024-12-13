package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivitySchedulePageDetailBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class SchedulePageDetail : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulePageDetailBinding
    private lateinit var scheduleDetail: ScheduleDetailBank
    private var selectedIndex: Int = -1
//    private var scheduleList: ArrayList<ScheduleDetailBank> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchedulePageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ambil data
        val nama_event = intent.getStringExtra("SCHEDULE_NAME")
        val gameName = intent.getStringExtra("GAME_NAME")
        val teamName = intent.getStringExtra("TEAM_NAME")
        val scheduleDate = intent.getStringExtra("SCHEDULE_DATE")
        selectedIndex = intent.getIntExtra("SCHEDULE_INDEX", -1)

        //tampilkan data
        binding.txtName.text = nama_event
        binding.txtGame.text = gameName
        binding.txtTeam.text = teamName
        binding.txtDateTime.text = scheduleDate

        loadScheduleDetail(nama_event)

        Log.d("SchedulePageDetail", "Schedule Name: $nama_event, Game Name: $gameName, Team Name: $teamName")

        binding.btnNotify.setOnClickListener(){
            Toast.makeText(this,"Notification Created", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadScheduleDetail(nama_event: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/schedule_detail.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                Log.d("SchedulePageDetail", "Received JSON: $it")
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    Log.d("SchedulePageDetail", "JSON data before parsing: ${data.toString()}")
                    val sType = object : TypeToken<List<ScheduleDetailBank>>() {}.type
                    val scheduleList: List<ScheduleDetailBank> = Gson().fromJson(data.toString(), sType)
                    Log.d("SchedulePageDetail", "Schedule list size: ${scheduleList.size}")

                    if (selectedIndex >= 0 && selectedIndex < scheduleList.size) {
                        scheduleDetail = scheduleList[selectedIndex]  // Ambil data berdasarkan indeks yang dipilih
                        binding.txtDescription.text = scheduleDetail.description
                        binding.txtLocation.text = scheduleDetail.location
                        binding.txtDateTime.text = scheduleDetail.time

                        Picasso.get()
                            .load(scheduleDetail.image_url)
                            .into(binding.imgEvent)
                    } else {
                        Toast.makeText(this, "Invalid schedule index", Toast.LENGTH_SHORT).show()

                        Log.d("SchedulePageDetail", "Selected index: $selectedIndex")
                    }

                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nama_event"] = nama_event?: "Unknown Schedule"
//                params["nama_game"] = gameName?: "Unknown Schedule"
//                params["nama_team"] = teamName?: "Unknown Schedule"
                return params
            }
        }

        queue.add(stringRequest)
    }


}