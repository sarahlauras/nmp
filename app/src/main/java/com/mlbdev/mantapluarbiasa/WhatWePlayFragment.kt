package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityOurScheduleBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhatWePlayBinding
import com.mlbdev.mantapluarbiasa.databinding.FragmentWhatWePlayBinding
import com.mlbdev.mantapluarbiasa.databinding.FragmentWhoWeAreBinding
import org.json.JSONObject

private val ARG_GAME = "arraygame"
class WhatWePlayFragment : Fragment() {
    private var whatwe_play: ArrayList<GameBank> = ArrayList()
    private lateinit var binding: FragmentWhatWePlayBinding
    private lateinit var listAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.apply()

        if(username != null) {
            readGame(username)  // Hanya butuh username untuk mengakses data
        } else {
            Toast.makeText(context, "Username not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatWePlayBinding.inflate(inflater, container, false)

        listAdapter = GameAdapter(whatwe_play)
        binding.recGames.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = listAdapter
        }

        return binding.root
    }

    // Fungsi untuk mengambil data game dari database
    fun readGame(username : String) {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/native/160422015/whatweplay.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<GameBank>>() {}.type
                    whatwe_play = Gson().fromJson(data.toString(), sType) as ArrayList<GameBank>
                    updateList()  // Update RecyclerView setelah data diterima
                } else {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username  // Kirimkan username hanya jika diperlukan
                return params
            }
        }
        queue.add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WhatWePlayFragment() // Tidak ada parameter
    }

    // Update RecyclerView setelah data diterima
    private fun updateList() {
        listAdapter = GameAdapter(whatwe_play)
        binding.recGames.adapter = listAdapter
    }
}
