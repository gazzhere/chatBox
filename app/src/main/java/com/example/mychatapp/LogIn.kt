package com.example.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnlogin:Button
    private lateinit var btnSignUp:Button
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()

        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnlogin=findViewById(R.id.btn_login)
        btnSignUp=findViewById(R.id.btn_signup)
        btnSignUp.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password);
        }

    }
    private fun login(email :String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                 val intent=Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn, "user does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}