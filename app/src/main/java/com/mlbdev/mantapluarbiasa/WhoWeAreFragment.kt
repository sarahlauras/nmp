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

//private val "GAME_INDEX" = "gameIndex"

class WhoWeAreFragment : Fragment() {

    private lateinit var binding: FragmentWhoWeAreBinding
    private var name: String? = null
    private var description: String? = null
    private var image: String? = null
    private var likeCount:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            gameIndex = it.getInt("GAME_INDEX", 1)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
    View? {
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
                        val whoWeAreList: List<WhoWeAreBank> = Gson().fromJson(data.toString(), sType)
                        if (whoWeAreList.isNotEmpty()) {
                            val data = whoWeAreList[0]
                            name = data.name
                            description = data.description
                            image = data.image
                            likeCount = data.like

                            updateUI()
                        }
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
        ) {
        }
        queue.add(stringRequest)
    }

    private fun updateUI() {
        binding.txtTeamName.text = name
        binding.txtDescription.text = description
        binding.btnLike.text = "Likes: $likeCount"  // Display the like count

        // Load image with Picasso
        if (image != null) {
            Picasso.get()
                .load(image) // Load the image URL
//                .placeholder(R.drawable.placeholder_image) // Optional placeholder image while loading
//                .error(R.drawable.error_image) // Optional error image if the URL is invalid
                .into(binding.imageTeam)
        }
    }

    data class WhoWeAre(
        val name: String,
        val description: String,
        val image: String,
        val like: Int  // Added like field
    )

    companion object {
        @JvmStatic
        fun newInstance() = WhoWeAreFragment()
    }
}