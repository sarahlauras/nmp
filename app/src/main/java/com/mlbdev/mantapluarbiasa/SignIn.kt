package com.mlbdev.mantapluarbiasa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mlbdev.mantapluarbiasa.databinding.ActivitySignInBinding
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah sudah login (ada idMember di SharedPreferences)
        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        val idMember = sharedPreferences.getInt("ID_MEMBER", -1) // Ambil idMember sebagai Int

        // Jika idMember sudah ada, langsung menuju MainActivity tanpa login
        if (idMember != -1) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Menutup SignIn activity agar tidak kembali ke halaman login
            return
        }

        binding.btnSubmit.setOnClickListener {
            val username = binding.txtUsername.text.toString() // mengambil user + pass
            val password = binding.txtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                signIn(username, password)// kalau ada user + pass langsung ke sign up
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent) //kalau klik sign up ya langsung ke sign up activity
        }
    }

    private fun signIn(username: String, password: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160422015/signin.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    val message = obj.getString("message")

                    if (status == "success") {
                        // Ambil idmember dan username dari response
                        val idMember = obj.getString("idmember")
                        val username = obj.getString("username")
                        val fname = obj.getString("fname")
                        val lname = obj.getString("lname")

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show() // membuat notifikasi

                        // Simpan idmember dan username ke SharedPreferences
                        val sharedPreferences = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt("ID_MEMBER", idMember.toInt())  // Simpan idmember sebagai Int
                        editor.putString("USERNAME", username) // Simpan username
                        editor.putString("FNAME", fname)
                        editor.putString("LNAME", lname)
                        editor.apply()

                        // Pindah ke MainActivity
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // Jika login gagal
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("Volley Error", error.toString())
                Toast.makeText(
                    this,
                    "Network Error: ${error.networkResponse?.statusCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> { // menyediakan param buat post, karna waktu manggil database perlu post
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }
}

