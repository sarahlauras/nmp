package com.mlbdev.mantapluarbiasa

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mlbdev.mantapluarbiasa.databinding.ActivityAchievementBinding
import org.json.JSONObject

class Achievement : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name") ?: ""

        getYearsFromServer(name)

        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterAchievements()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun getYearsFromServer(name:String){
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
                        val years = mutableListOf("All")

                        // Loop through the JSON array and extract years
                        for (i in 0 until data.length()) {
                            val yearObject = data.getJSONObject(i) // Ambil objek JSON
                            val year = yearObject.getString("year(a.date)") // Ambil nilai tahun menggunakan key
                            years.add(year)
                        }

                        // Update the spinner with the years
                        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
                        binding.spinnerYear.adapter = adapter
                        binding.spinnerYear.setSelection(0) // Set default selection to "All"
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

//    private fun updateYearSpinner(gameIndex:Int){
//        val years = mutableListOf("All") + AchievementData.achievement
//            .filter { it.gamesIndex == gameIndex }
//            .map { it.year }
//            .distinct()
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
//        binding.spinnerYear.adapter = adapter
//        binding.spinnerYear.setSelection(0)
//    }

    private fun filterAchievements() {
        val selectedYear = binding.spinnerYear.selectedItem.toString()

        // Filter achievements based on selected year
        val filteredAchievements = AchievementData.achievement.filter {
            selectedYear == "All" || it.year == selectedYear
        }

        val formattedAchievements = filteredAchievements
            .sortedBy { it.year }
            .mapIndexed { index, achievement ->
                "${index + 1}. ${achievement.achievement} (${achievement.year}) - ${achievement.team}"
            }
            .joinToString("\n")

        binding.txtAchievement.text = formattedAchievements
    }
}