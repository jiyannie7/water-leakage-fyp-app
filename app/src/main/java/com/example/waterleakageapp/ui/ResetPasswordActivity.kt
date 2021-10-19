 package com.example.waterleakageapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.waterleakageapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private val tag = "ResetPasswordActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        auth = Firebase.auth
        resetEmail()
        backButton()

    }//onCreate
    private fun backButton(){
        val actionBar = supportActionBar
        actionBar!!.title = "Reset password"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        return true
    }
    
    private fun resetEmail(){
        val btnReset : Button = findViewById(R.id.btn_reset)
        val emailReset : EditText = findViewById(R.id.et_email_reset)

        btnReset.setOnClickListener {


            if (emailReset.text.toString().trim().isEmpty()){
                emailReset.error  = "Enter your email"
                emailReset.requestFocus()
                return@setOnClickListener

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailReset.text.toString()).matches()) {
                emailReset.error = "Wrong email format"
                emailReset.requestFocus()
                return@setOnClickListener
            }

            else{
                auth.sendPasswordResetEmail(emailReset.text.toString().trim())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Log.d(tag,"Email sent")
                            Toast.makeText(this@ResetPasswordActivity,"Check email to reset password",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,LoginActivity::class.java ))

                        }else{
                            Toast.makeText(this@ResetPasswordActivity,"Email does not exist. Please try again.",Toast.LENGTH_SHORT).show()
                        }
                    }
            }//AUTH
        }
    }
}