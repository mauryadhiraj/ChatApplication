package com.data.chatapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth=FirebaseAuth.getInstance()   //intialize firbase

        supportActionBar?.hide()          //for hide action bar

        edtName=findViewById(R.id.editTextName)
        edtEmail=findViewById(R.id.editTextEmail)
        edtPassword=findViewById(R.id.editTextPassword)
        btnsignup=findViewById(R.id.btnSignup)

        btnsignup.setOnClickListener{
            val name=edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            signUp(name,email,password)
        }
    }
    private fun signUp(name:String,email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code jump to home
                   adduserToDatabase(name,email,auth.currentUser?.uid!!) //null safe
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Some Error Occured",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun adduserToDatabase(name:String,email:String,uid:String){

        dbRef=FirebaseDatabase.getInstance().getReference()//Reference of database
        dbRef.child("user").child(uid).setValue(User(name,email,uid))//Add user to database and set the value
    }
}