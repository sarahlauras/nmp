package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityApplyTeamNewBinding
import org.json.JSONObject

class ApplyTeamNewActivity : AppCompatActivity() {
    var games: ArrayList<GameBank> = ArrayList()
    var selectedTeamId: Int? = null  // Menyimpan idteam yang dipilih
    private lateinit var binding: ActivityApplyTeamNewBinding
    var selectedGameName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyTeamNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = this.getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)
        val idMember = intent.getIntExtra("ID_MEMBER", -1)  // Ambil idMember dari Intent

        readGame()

        binding.spinnerGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedGameName = games[position].name
                readTeam(selectedGameName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.btnApplyTeam.setOnClickListener {
            selectedTeamId?.let {
                val description = binding.txtDesc.text.toString()
                addProposal(idMember, it.toString(), description) // Gunakan selectedTeamId langsung
            } ?: run {
                Toast.makeText(this, "Please select a team first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readGame() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/games.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<GameBank>>() {}.type
                    games = Gson().fromJson(data.toString(), sType) as ArrayList<GameBank>

                    val names = getNameGames()  // Panggil fungsi getNameGames untuk mendapatkan nama-nama game
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
                    binding.spinnerGame.adapter = adapter
                    binding.spinnerGame.setSelection(0)
                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            { error -> Log.e("apiresult", error.message.toString()) }
        ){}
        queue.add(stringRequest)
    }

    private fun readTeam(name: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/applyteamnew_team.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    if (data.length() > 0) {
                        val teamsList = mutableListOf<String>()  // List nama tim

                        // Mengambil data tim dari response dan menyimpannya dalam list teams
                        for (i in 0 until data.length()) {
                            val teamObj = data.getJSONObject(i)
                            val teamName = teamObj.getString("team")  // Mendapatkan nama tim
                            val idteam = teamObj.getInt("idteam")  // Mendapatkan idteam

                            // Menyimpan idteam yang dipilih dari spinner
                            if (i == 0) { // Misalnya kita langsung pilih tim pertama
                                selectedTeamId = idteam
                            }

                            teamsList.add(teamName)  // Menambahkan nama tim ke spinner
                        }

                        // Mengatur adapter spinner dengan nama tim
                        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teamsList)
                        binding.spinnerTeam.adapter = adapter

                        // Menambahkan listener pada spinner
                        binding.spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                                // Menyimpan idteam yang dipilih ke selectedTeamId dari response
                                selectedTeamId = data.getJSONObject(position).getInt("idteam")
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    } else {
                        Toast.makeText(this, "No team data found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Failed to fetch team data", Toast.LENGTH_SHORT).show()
                }
            },
            { error -> Log.e("Volley Error", error.toString()) }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["game"] = name  // Kirim nama game sebagai parameter
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun getNameGames(): List<String> {
        // Mengembalikan nama-nama game dari list games
        return games.map { it.name }
    }

    private fun addProposal(idMember: Int, idteam: String, description: String) {
        Log.d("RequestParams", "idMember: $idMember, idTeam: $selectedTeamId, description: $description")

        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/applyteamnew_add.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    val message = obj.getString("message")

                    if (status == "success") {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, ApplyTeamActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Log.e("Volley Error", error.toString())
                Toast.makeText(this, "Network Error: ${error.networkResponse?.statusCode}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["idmember"] = idMember.toString()
                params["idteam"] = idteam  // Gunakan selectedTeamId yang sudah ada
                params["description"] = description
                return params
            }
        }
        queue.add(stringRequest)
    }
}

