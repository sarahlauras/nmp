package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val username = binding.txtUsername
            val password = binding.txtPassword
            signUp(username.toString(), password.toString())
        }
    }

    fun signUp(username: String, password: String) {
        val hashedPassword = password.hashCode().toString()
        val editor = sharedPreferences.edit()
        editor.putString("Username", username)
        editor.putString("Password", hashedPassword)
        editor.apply()
        Toast.makeText(this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show()
    }

}