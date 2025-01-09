package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityTeamsBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhatWePlayBinding
import com.mlbdev.mantapluarbiasa.databinding.DrawerLayoutTeamsBinding
import com.mlbdev.mantapluarbiasa.databinding.TeamsCardBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class Teams : AppCompatActivity() {
    private lateinit var binding: DrawerLayoutTeamsBinding
    var teams: ArrayList<TeamBank> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerLayoutTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.Teams.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val idgame = intent.getStringExtra("idgame") ?: ""

        Log.d("Teams", "Received idgame: $idgame")
        val idgames = idgame.toInt()
        getTeam(idgames)

        val imageId= intent.getStringExtra("image") ?:""
        if (imageId.isNotEmpty()) {
            Picasso.get()
                .load(imageId)  // Memuat gambar dari URL atau path yang didapat
                .into(binding.Teams.imgGame)  // Menampilkan gambar di ImageView
        }
    }

    private fun getTeam(idgame: Int) {
        Log.d("getTeam", "Fetching data for idgame: $idgame")
        

        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/teamongames.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                Log.d("getTeam", "Response received: $response")

                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<TeamBank>>() {}.type
                    teams = Gson().fromJson(data.toString(), sType) as ArrayList<TeamBank>
                    Log.d("getTeam", "Data parsed successfully, total items: ${teams.size}")
                    updateList()
                } else {
                    Log.d("getTeam", "No data found")
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("getTeam", "Error: ${error.message}")
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["idgame"] = idgame.toString()
                Log.d("getTeam", "Request params: $params")
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun updateList() {
        Log.d("Teams", "Updating RecyclerView with ${teams.size} items")
        val lm = LinearLayoutManager(this)
        binding.Teams.teamsPage.apply {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = TeamAdapter(teams)
        }
    }
}