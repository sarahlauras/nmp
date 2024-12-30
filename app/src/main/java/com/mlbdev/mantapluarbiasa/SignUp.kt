package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mlbdev.mantapluarbiasa.databinding.ActivitySignUpBinding
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            val fname = binding.txtFirstname.text.toString()
            val lname = binding.txtLastname.text.toString()
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            val repassword = binding.txtRepeatPassword.text.toString()

            if (fname.isNotEmpty() && lname.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()) {
                if (password == repassword) {
                    signUp(fname, lname, username, password)
                } else {
                    Toast.makeText(this, "Password dan Re-password beda", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSubmit.isEnabled = isChecked
        }

    }

    fun signUp(fname: String, lname: String, username: String, password: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/signup.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    val message = obj.getString("message")

                    if (status == "success") {
                        // Ambil idmember dari response
                        val idMember = obj.getString("idmember")
                        Log.d("IDMEMBER", "$idMember")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        // Simpan idmember ke SharedPreferences
                        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("IDMEMBER", idMember)  // Simpan idmember
                        editor.apply()


                        val intent = Intent(this, SignIn::class.java)
                        startActivity(intent)
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
                params["fname"] = fname
                params["lname"] = lname
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }
}

