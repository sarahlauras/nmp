package com.mlbdev.mantapluarbiasa

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
        // Pastikan bahwa list tidak kosong
        if (whoWeAreList.isNotEmpty()) {
            val team = whoWeAreList[0] // Ambil data pertama untuk saat ini

            // Update UI dengan data
            binding.txtTeamName.text = team.name
            binding.txtDescription.text = team.description

            // Set Like button text jika perlu
            //binding.btnLike.text = "Likes: ${team.likeCount}"

            // Load gambar dengan Picasso
            Picasso.get()
                .load(team.image) // Ganti dengan URL gambar yang diterima
                .into(binding.imageTeam)
        }
        else{
            binding.txtDescription.text = "KOSONG"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WhoWeAreFragment()
    }
}
