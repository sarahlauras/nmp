package com.mlbdev.mantapluarbiasa

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
import com.mlbdev.mantapluarbiasa.databinding.ActivityApplyTeamNewBinding
import org.json.JSONObject

class ApplyTeamNewActivity : AppCompatActivity() {
    var games: ArrayList<GameBank> = ArrayList()
    private lateinit var binding: ActivityApplyTeamNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyTeamNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun readGame() {
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
}