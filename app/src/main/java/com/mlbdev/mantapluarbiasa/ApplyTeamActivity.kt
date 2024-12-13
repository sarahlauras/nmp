package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityApplyTeamBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityMemberBinding
import org.json.JSONObject

//private val ARG_APPLYTEAM = "arrayapplyteam"

class ApplyTeamActivity : AppCompatActivity() {
    var apply_team: ArrayList<ApplyTeamBank> = ArrayList()
    private lateinit var binding: ActivityApplyTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil username dari SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)
        val idMember = sharedPreferences.getInt("ID_MEMBER", -1)  // Ambil idMember


        if (username != null) {
            readSchedule(username)
        } else {
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, ApplyTeamNewActivity::class.java)
            intent.putExtra("ID_MEMBER", idMember)  // Pass idMember ke activity selanjutnya
            startActivity(intent)
        }
    }

    private fun readSchedule(username: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/applyteam.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<ApplyTeamBank>>() {}.type
                    apply_team = Gson().fromJson(data.toString(), sType) as ArrayList<ApplyTeamBank>
                    updateList()
                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("apiresult", error.message.toString())
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username // Tambahkan parameter username
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun updateList() {
        val lm = LinearLayoutManager(this)
        binding.recProposal.apply {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = ApplyTeamAdapter(apply_team)
        }
    }
}