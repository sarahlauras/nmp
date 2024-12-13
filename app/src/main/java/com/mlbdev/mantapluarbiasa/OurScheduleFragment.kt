package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Playlists
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlbdev.mantapluarbiasa.databinding.ActivityOurScheduleBinding
import org.json.JSONObject

private val ARG_SCHEDULE = "arrayschedule"

class OurScheduleFragment : Fragment() {
    var our_schedule: ArrayList<OurScheduleBank> = ArrayList()
    lateinit var binding: ActivityOurScheduleBinding
    lateinit var listAdapter: OurScheduleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)
        Log.d("YOKUSERNAMEYOK", "Username: $username")
        if (username != null && username.isNotEmpty()) {
            readSchedule()
        } else {
            Toast.makeText(context, "Username not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityOurScheduleBinding.inflate(inflater, container, false)
        listAdapter = OurScheduleAdapter(our_schedule)
        binding.schedulePage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = listAdapter
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = OurScheduleFragment()
    }

    private fun readSchedule() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/native/160422015/ourschedule.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                Log.d("apiresult", "Response: $response")
                val obj = JSONObject(response)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<OurScheduleBank>>() {}.type
                    our_schedule = Gson().fromJson(data.toString(), sType) as ArrayList<OurScheduleBank>
                    updateList()
                    Log.d("BERHASIL", "updatelist")
                } else {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("apiresult", "Error: ${error.message}")
                Toast.makeText(context, "Error connecting to server", Toast.LENGTH_SHORT).show()
            }
        ) {
            // Tidak perlu getParams atau getBody, tinggal kirim request ke server tanpa parameter tambahan
        }

        queue.add(stringRequest)
    }


    fun updateList(){
        listAdapter = OurScheduleAdapter(our_schedule)
        binding.schedulePage.adapter = listAdapter
        Log.d("OurScheduleFragment", "Updated list size: ${our_schedule.size}")
    }
}