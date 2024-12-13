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
    var teams: ArrayList<TeamBank> = ArrayList()
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

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val description = binding.txtDesc.text
        binding.btnApplyTeam.setOnClickListener {
            val selectedTeam = teams[binding.spinnerTeam.selectedItemPosition]
            val idteam = selectedTeam.idteam
            addProposal(idMember, idteam.toString(), description.toString())
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

                    val names = games.getNameGames()
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
                    binding.spinnerGame.adapter = adapter
                    binding.spinnerGame.setSelection(0)
                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        ){}
        queue.add(stringRequest)
    }

    private fun readTeam(name: String) {
        // Menampilkan spinnerTeam setelah memilih game
        binding.spinnerTeam.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/applyteamnew_team.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val teamsList = mutableListOf<String>()  // Menyimpan nama-nama tim

                    // Loop untuk mengambil idteam dan nama tim dari JSON
                    for (i in 0 until data.length()) {
                        val teamObj = data.getJSONObject(i)
                        val teamName = teamObj.getString("team")  // Mendapatkan nama tim
                        val idteam = teamObj.getInt("idteam")  // Mendapatkan idteam

                        // Menambahkan objek TeamBank ke dalam list teams
                        teams.add(TeamBank(idteam, 0, teamName, "", "", 0, ""))

                        // Menyimpan nama tim untuk ditampilkan di spinner
                        teamsList.add(teamName)
                    }

                    // Menampilkan nama tim di spinnerTeam
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teamsList)
                    binding.spinnerTeam.adapter = adapter
                    binding.spinnerTeam.setSelection(0)
                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["game"] = name // Mengirimkan nama game yang dipilih
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun List<GameBank>.getNameGames(): List<String> {
        return this.map { it.name }
    }

    private fun List<TeamBank>.getNameTeams(): List<String> {
        return this.map { it.nameteam }
    }

    private fun addProposal(idmember:Int, idteam:String, description:String){
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
                val params = HashMap<String, String>()
                params["idmember"] = idmember.toString()
                params["idteam"] = idteam
                params["description"] = description
                return params
            }
        }
        queue.add(stringRequest)
    }
}