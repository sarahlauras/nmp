package com.mlbdev.mantapluarbiasa

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
import com.mlbdev.mantapluarbiasa.databinding.ActivityMemberBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    var members: ArrayList<MembersBank> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idteam = intent.getStringExtra("idteam") ?: ""  //ambil idteam
        Log.d("Members", "Received idteam: $idteam")
        val idteams = idteam.toIntOrNull() ?: 0  // Konversi idteam ke Int
        if (idteams != 0) {
            getMember(idteams)  // Panggil fungsi dengan idteam
        } else {
            Toast.makeText(this, "Invalid Team ID", Toast.LENGTH_SHORT).show()
        }
        val imageId= intent.getStringExtra("image") ?:""
        if (imageId.isNotEmpty()) {
            Picasso.get()
                .load(imageId)  // Memuat gambar
                .into(binding.imgGame)  // Menampilkan gambar
        }

    }

    private fun getMember(idteam: Int) {  // Terima idteam sebagai parameter
        Log.d("getMember", "Fetching data for idteam: $idteam")

        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/teammember.php"  

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                Log.d("getMember", "Response received: $response")

                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<MembersBank>>() {}.type
                    members = Gson().fromJson(data.toString(), sType) as ArrayList<MembersBank>
                    Log.d("getMember", "Data parsed successfully, total items: ${members.size}")

                    val teamName = data.getJSONObject(0).getString("team")
                    Log.d("teamName", "Fetched teamName: $teamName")

                    if (teamName.isNotEmpty()) {
                        binding.txtTeams.text ="Team: " + teamName
                    } else {
                        Log.d("teamName", "teamName is empty or missing in response")
                    }

                    updateList()
                } else {
                    Log.d("getMember", "No data found")
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }

            },
            { error ->
                Log.e("getMember", "Error: ${error.message}")
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["idteam"] = idteam.toString()
                Log.d("getMember", "Request params: $params")
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun updateList() {
        if (members.isEmpty()) {
            Log.d("Members", "No members data to show.")
            return
        }

        Log.d("Members", "Updating RecyclerView with ${members.size} items")

        val lm = LinearLayoutManager(this)
        binding.membersPage.layoutManager = lm
        binding.membersPage.setHasFixedSize(true)
        binding.membersPage.adapter = MemberAdapter(members)  // Set adapter
    }

}