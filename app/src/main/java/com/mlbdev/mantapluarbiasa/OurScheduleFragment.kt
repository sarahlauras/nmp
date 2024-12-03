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
    private lateinit var binding: ActivityOurScheduleBinding
    //private lateinit var listAdapter: OurScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            our_schedule = it.getParcelableArrayList<OurScheduleBank>(ARG_SCHEDULE) as ArrayList<OurScheduleBank>
//        }
        val sharedPreferences = requireContext().getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", null)

        if(username != null){
            readSchedule(username)
        }
        else{
            Toast.makeText(context, "Username not found", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityOurScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(schedule:ArrayList<OurScheduleBank>) = OurScheduleFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_SCHEDULE, schedule)
            }
        }
    }

    fun readSchedule(username: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/native/160422015/ourschedule.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<OurScheduleBank>>() {}.type
                    our_schedule = Gson().fromJson(data.toString(), sType) as ArrayList<OurScheduleBank>
                    updateList()
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
                params["username"] = username // Tambahkan parameter username
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun updateList(){
        val lm = LinearLayoutManager(activity)
        with(binding.schedulePage){
            layoutManager = lm
            setHasFixedSize(true)
            adapter = OurScheduleAdapter(our_schedule)
        }
    }
}