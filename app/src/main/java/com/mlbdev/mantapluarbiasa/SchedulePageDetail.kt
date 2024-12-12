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
import org.json.JSONObject

class SchedulePageDetail : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulePageDetailBinding
    private lateinit var scheduleDetail: ScheduleDetailBank
    private var selectedIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchedulePageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ambil data
        val scheduleName = intent.getStringExtra("SCHEDULE_NAME")
        val gameName = intent.getStringExtra("GAME_NAME")
        val teamName = intent.getStringExtra("TEAM_NAME")
        val scheduleDate = intent.getStringExtra("SCHEDULE_DATE")
        selectedIndex = intent.getIntExtra("SCHEDULE_INDEX", -1)

        //tampilkan data
        binding.txtName.text = scheduleName
        binding.txtGame.text = gameName
        binding.txtTeam.text = teamName
        binding.txtDateTime.text = scheduleDate

        loadScheduleDetail()


        binding.btnNotify.setOnClickListener(){
            Toast.makeText(this,"Notification Created", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadScheduleDetail() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/schedule_detail.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<ScheduleDetailBank>>() {}.type
                    val scheduleList: List<ScheduleDetailBank> = Gson().fromJson(data.toString(), sType)

                    if (selectedIndex >= 0 && selectedIndex < scheduleList.size) {
                        scheduleDetail = scheduleList[selectedIndex]  // Ambil data berdasarkan indeks yang dipilih
                        binding.txtDescription.text = scheduleDetail.description
                        binding.txtLocation.text = scheduleDetail.location
                        binding.txtDateTime.text = scheduleDetail.time
                    } else {
                        Toast.makeText(this, "Invalid schedule index", Toast.LENGTH_SHORT).show()
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
                //params["username"] = username // Tambahkan parameter username
                return params
            }
        }

        queue.add(stringRequest)
    }

}