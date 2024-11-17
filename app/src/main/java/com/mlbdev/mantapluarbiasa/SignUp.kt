package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //jika btnSubmit dipencet
        binding.btnSubmit.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty()){
                signUp(username, password)
                val intent = Intent(this, SignIn::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signUp(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val hashedPassword = password.hashCode().toString()

        editor.putString("Username", username)
        editor.putString("Password", hashedPassword)
        editor.apply()

        Toast.makeText(this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show()
    }

}