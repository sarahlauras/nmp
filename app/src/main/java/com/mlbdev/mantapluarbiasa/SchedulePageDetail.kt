package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.mlbdev.mantapluarbiasa.databinding.ActivitySchedulePageDetailBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class SchedulePageDetail : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulePageDetailBinding
    private lateinit var scheduleDetail: ScheduleDetailBank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchedulePageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dari Intent
        val namaEvent = intent.getStringExtra("SCHEDULE_NAME")
        val gameName = intent.getStringExtra("GAME_NAME")
        val teamName = intent.getStringExtra("TEAM_NAME")
        val scheduleDate = intent.getStringExtra("SCHEDULE_DATE")
        val idevent = intent.getStringExtra("IDEVENT") // Ambil idevent
        val idteam = intent.getIntExtra("IDTEAM", -1)  // Ambil idteam

        // Menampilkan data dasar di UI
        binding.txtName.text = namaEvent
        binding.txtGame.text = gameName
        binding.txtTeam.text = teamName
        binding.txtDateTime.text = scheduleDate

        if (idevent != null && idteam != -1) {
            loadScheduleDetail(idevent, idteam)
        } else {
            Toast.makeText(this, "Invalid event or team data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadScheduleDetail(idevent: String, idteam: Int) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/schedule_detail.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("result") == "OK") {
                        val data = obj.getJSONArray("data")
                        if (data.length() > 0) {
                            val scheduleDetailJson = data.getJSONObject(0).toString()
                            scheduleDetail = Gson().fromJson(scheduleDetailJson, ScheduleDetailBank::class.java)

                            // Menampilkan detail data ke UI
                            binding.txtDescription.text = scheduleDetail.description
                            binding.txtLocation.text = scheduleDetail.location
                            binding.txtDateTime.text = scheduleDetail.time

                            Picasso.get()
                                .load(scheduleDetail.image_url)
                                .into(binding.imgEvent)
                        } else {
                            Toast.makeText(this, "No details found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("apiresult", "Error parsing response: ${e.message}")
                }
            },
            { error ->
                Log.e("apiresult", error.message.toString())
                Toast.makeText(this, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "idevent" to idevent,
                    "idteam" to idteam.toString()
                )
            }
        }
        queue.add(stringRequest)
    }
}
