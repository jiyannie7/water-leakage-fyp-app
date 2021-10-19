package com.example.waterleakageapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.waterleakageapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val tag = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        registerAccount()
        loginAccount()
        tvPassword()

    }//onCreate
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun registerAccount(){

        val txtNotRegistered = findViewById<TextView>(R.id.tv_not_registered)
        txtNotRegistered.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun loginAccount(){

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val txtLoginEmail = findViewById<EditText>(R.id.et_login_email)
        val txtLoginPwd = findViewById<EditText>(R.id.et_login_password)
        btnLogin.setOnClickListener {

            if (txtLoginEmail.text.toString().isEmpty()) {
                txtLoginEmail.error = "Please enter email"
                txtLoginEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(txtLoginEmail.text.toString()).matches()) {
                txtLoginEmail.error = "Please enter valid email"
                txtLoginEmail.requestFocus()
                return@setOnClickListener
            }
            if (txtLoginPwd.text.toString().isEmpty()) {
                txtLoginPwd.error = "Please enter password"
                txtLoginPwd.requestFocus()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(txtLoginEmail.text.toString(), txtLoginPwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag,"Authentication Successful")
                        val user = auth.currentUser
                        startActivity(Intent(this,MainPageActivity::class.java))
                        updateUI(user)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                            Toast.makeText(this,"Incorrect email address or password",Toast.LENGTH_LONG).show()
                        Log.d(tag,"Authentication failed")
                        updateUI(null)
                    }
                }
        }
    }
    private fun tvPassword(){
        val txtForgotPwd = findViewById<TextView>(R.id.tv_forgot_pwd)
        txtForgotPwd.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }

    private fun closeKeyboard(){
        val view = this.currentFocus
        if(view!=null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
    private fun updateUI (currentUser: FirebaseUser?){

        if(currentUser != null)
        {
            startActivity(Intent(this, MainPageActivity::class.java))
            finish()
        }


    } //updateUI


}//AppCompatActivity
