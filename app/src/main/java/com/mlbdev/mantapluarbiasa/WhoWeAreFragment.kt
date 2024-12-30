package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.mlbdev.mantapluarbiasa.databinding.FragmentWhoWeAreBinding
import org.json.JSONObject

class WhoWeAreFragment : Fragment() {

    private lateinit var binding: FragmentWhoWeAreBinding
    private var whoWeAreList: List<WhoWeAreBank> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhoWeAreBinding.inflate(inflater, container, false)
        fetchTeamData()
        binding.btnLike.setOnClickListener {
            addLikes()
            val team = whoWeAreList[0]
            team.likes = team.likes + 1
            binding.btnLike.text = team.likes.toString()
        }
        return binding.root
    }

    private fun fetchTeamData() {
        val url = "https://ubaya.xyz/native/160422015/whoweare.php"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("result") == "OK") {
                        val data = obj.getJSONArray("data")
                        val sType = object : TypeToken<List<WhoWeAreBank>>() {}.type
                        whoWeAreList = Gson().fromJson(data.toString(), sType)
                        Log.d("WhoWeAre", "Received like: $whoWeAreList")
                        // Menampilkan data ke UI
                        updateUI()
                    } else {
                        Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {}
        queue.add(stringRequest)
    }

    private fun updateUI() {
        if (whoWeAreList.isNotEmpty()) {
            val team = whoWeAreList[0]
            Log.d("Members", "Received idteam: $team")

            binding.txtTeamName.text = team.name
            binding.txtDescription.text = team.description
            binding.btnLike.text = team.likes.toString()

            Picasso.get()
                .load(team.image)
                .into(binding.imageTeam)
        }
        else{
            binding.txtDescription.text = "KOSONG"
        }
    }

    private fun addLikes() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/native/160422015/addlikes.php"
        Log.d("Members", "masuk addlikes ini")

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    val message = obj.getString("message")

                    if (status == "success") {

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Log.e("Volley Error", error.toString())
                Toast.makeText(requireContext(), "Network Error: ${error.networkResponse?.statusCode}", Toast.LENGTH_SHORT).show()
            }
        ) {

        }
        queue.add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WhoWeAreFragment()
    }
}
