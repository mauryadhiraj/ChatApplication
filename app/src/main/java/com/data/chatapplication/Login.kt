package com.data.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnsignup:Button
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()  //intialize firbase

        supportActionBar?.hide()         // for hide action

        edtEmail=findViewById(R.id.editTextEmail)
        edtPassword=findViewById(R.id.editTextPassword)
        btnLogin=findViewById(R.id.btnLogin)
        btnsignup=findViewById(R.id.btnSignup)

        btnsignup.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {    //this is login part
                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }

    }
}