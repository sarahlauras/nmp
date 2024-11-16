package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivitySignInBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivitySignUpBinding

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val isSuccessful = signIn(username, password)
                if (isSuccessful) {
                    Toast.makeText(this, "Sign-In Successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn(username: String, password: String): Boolean {
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val storedUsername = sharedPreferences.getString("Username", null)
        val storedPassword = sharedPreferences.getString("Password", null)
        val hashedPassword = password.hashCode().toString()

        return if (username == storedUsername && hashedPassword == storedPassword) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("IsLoggedIn", true) // Simpan status login
            editor.apply()
            true
        } else {
            false
        }
    }
}