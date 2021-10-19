package com.example.waterleakageapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val tag = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")

        registerUser()
        hasRegistered()

    }//onCreate
    /*private fun sendEmailVerification(){
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this){
                //Email verification sent
            }
    }*/
    private fun registerUser(){
        val btnRegister = findViewById<Button>(R.id.btn_register)

        //btn
        btnRegister.setOnClickListener {

            val txtRegisterEmail = findViewById<EditText>(R.id.et_register_email)
            val txtRegisterPwd = findViewById<EditText>(R.id.et_register_password)
            val txtContact = findViewById<EditText>(R.id.et_register_contact)
            val txtName = findViewById<EditText>(R.id.et_register_name)
            val txtRegisterPwd2 = findViewById<EditText>(R.id.et_register_password2)

            /*if (txtName.text.toString().isEmpty()) {
                txtName.error = "Please enter your name"
                txtName.requestFocus()
                return@setOnClickListener
            }
            */

            /*
            if (txtRegisterEmail.text.toString().isEmpty()) {
                txtRegisterEmail.error = "Please enter email"
                txtRegisterEmail.requestFocus()
                return@setOnClickListener

            }

            if (txtContact.text.toString().isEmpty()) {
                txtContact.error = "Please enter contact number"
                txtContact.requestFocus()
                return@setOnClickListener

            }

            if (txtRegisterPwd.text.toString().isEmpty()) {
                txtRegisterPwd.error = "Please provide password"
                txtContact.requestFocus()
                return@setOnClickListener
            }*/

                if(txtName.text.toString().trim().isEmpty()  and txtContact.text.toString().trim().isEmpty() and txtRegisterEmail.text.toString().trim().isEmpty() and txtRegisterPwd.text.toString().trim().isEmpty() and txtRegisterPwd2.text.toString().trim().isEmpty()){
                    txtName.error = "Please enter your name"
                    txtContact.error = "Please enter contact number"
                    txtRegisterEmail.error = "Please enter your email address"
                    txtRegisterPwd.error = "Please provide password"
                    txtRegisterPwd2.error = "Please provide password"
                    txtName.requestFocus()
                    txtContact.requestFocus()
                    txtRegisterEmail.requestFocus()
                    txtRegisterPwd.requestFocus()
                    txtRegisterPwd2.requestFocus()
                    Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

            if (!Patterns.EMAIL_ADDRESS.matcher(txtRegisterEmail.text.toString()).matches()) {
                txtRegisterEmail.error = "Please enter valid email"
                txtRegisterEmail.requestFocus()
                return@setOnClickListener

            }
            if (txtRegisterPwd.text.toString() != txtRegisterPwd2.text.toString()) {
                txtRegisterPwd2.error = "Password not match!"
                txtRegisterPwd2.requestFocus()
                return@setOnClickListener
            }

            //authenticate users for firebase
            else{
                auth.createUserWithEmailAndPassword(txtRegisterEmail.text.toString(),
                    txtRegisterPwd.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(tag, "Successful")
                            sendData()
                            Toast.makeText(applicationContext,
                                "Account created successfully",
                                Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainPageActivity::class.java))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(tag, "Unsuccessful")
                            Toast.makeText(this, "Register failed. Please try again",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
        }
        }
    }

    private fun sendData(){
        val txtRegisterEmail = findViewById<EditText>(R.id.et_register_email)
        val txtContact = findViewById<EditText>(R.id.et_register_contact)
        val txtName = findViewById<EditText>(R.id.et_register_name)

        val name = txtName.text.toString().trim()
        val email = txtRegisterEmail.text.toString().trim()
        val contact = txtContact.text.toString().trim()
        val userId = Firebase.auth.currentUser
        val id = userId!!.uid

        //send data to firebase
        val user = Users(name,email,contact)
        reference.child(id).setValue(user)
    }
    private fun hasRegistered(){
        val tvRegister = findViewById<TextView>(R.id.tv_has_registered)
        tvRegister.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

}//AppCompat