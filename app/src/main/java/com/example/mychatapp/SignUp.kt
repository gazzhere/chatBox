package com.example.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()
        edtName=findViewById(R.id.edt_name)
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnSignUp=findViewById(R.id.btn_signup)
        btnSignUp.setOnClickListener {
            val name=edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            signUp(name,email,password)
        }

    }
    private fun signUp(name:String,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserTodatabase(name,email,mAuth.currentUser?.uid!!);
                    // Sign in success, update UI with the signed-in user's information
                    val intent =Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this@SignUp, "some error occured", Toast.LENGTH_SHORT).show()
//                    Log.ERROR
                    Toast.makeText(this@SignUp, "err"+ task.getException()?.message, Toast.LENGTH_LONG).show();

                }
            }
    }
    private fun addUserTodatabase(name:String,email:String,uid:String){
     mdbref=FirebaseDatabase.getInstance().getReference()
        mdbref.child("user").child(uid).setValue(user(name,email,uid))
    }
}