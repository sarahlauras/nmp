package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityAchievementBinding
import com.mlbdev.mantapluarbiasa.databinding.DrawerLayoutAchievementsBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class Achievement : AppCompatActivity() {
    private lateinit var binding: DrawerLayoutAchievementsBinding
    private var achiev: ArrayList<AchievementBank> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerLayoutAchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.Achievement.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val name = intent.getStringExtra("name") ?: ""
        binding.Achievement.txtGame.text = name

        getImgAndYearsFromServer(name)

        binding.Achievement.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDate = if (position == 0) "" else parent.getItemAtPosition(position).toString()
                getAchievement(name,selectedDate)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun getImgAndYearsFromServer(name:String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/achievementyear.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            { response ->
                try {
                    // Parse the response into a JSON object
                    val jsonResponse = JSONObject(response)
                    if (jsonResponse.getString("result") == "OK") {
                        // Get the data from the response
                        val data = jsonResponse.getJSONArray("data")
                        val years = mutableListOf<String>()
                        var img : String ? =  null

                        // Loop through the JSON array and extract years
                        for (i in 0 until data.length()) {
                            val dataObject = data.getJSONObject(i) // Ambil objek JSON

                            if(dataObject.has("date")){
                                val year = dataObject.getString("date") // Ambil nilai tahun menggunakan key
                                years.add(year)
                            }
                            if(dataObject.has("image")){
                                img = dataObject.getString("image")
                            }
                        }
                        img?.let{
                            Picasso.get()
                                .load(it)
                                .into(binding.Achievement.imgPreview)
                        }

                        val sortedYears = years.toList().sorted()
                        val finalYears = mutableListOf("All").apply{addAll(sortedYears)}

                        // Update the spinner with the years
                        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, finalYears)
                        binding.Achievement.spinnerYear.adapter = adapter
                        binding.Achievement.spinnerYear.setSelection(0) // Set default selection to "All"
                    } else {
                        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("VolleyError", error.message.toString())
                Toast.makeText(this, "Request failed", Toast.LENGTH_SHORT).show()
            }) {
            // Add the "gname" parameter to the POST request
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["name"] = name
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun getAchievement(name:String, date:String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/achievementselectyear.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    if (jsonResponse.getString("result") == "OK") {
                        val data = jsonResponse.getJSONArray("data")
                        val sType = object : TypeToken<List<AchievementBank>>() {}.type

                        achiev = Gson().fromJson(data.toString(), sType) as ArrayList<AchievementBank>
                        filterAchievements()
                    } else {
                        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("VolleyError", error.message.toString())
                Toast.makeText(this, "Request failed", Toast.LENGTH_SHORT).show()
            }) {
            // Add the "gname" parameter to the POST request
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["name"] = name
                params["date"] = date
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun filterAchievements() {
        val formattedAchievements = achiev
            .sortedBy { it.date }
            .mapIndexed { index, achievement ->
                "${index + 1}. ${achievement.name} (${achievement.date}) - ${achievement.team}"
            }
            .joinToString("\n")

        binding.Achievement.txtAchievement.text = formattedAchievements
    }

}