package com.example.waterleakageapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    internal var user : Users? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
        setData()
        resetPassword()
        backButton()
    }
    private fun setData(){
        val menuListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(Users::class.java)
                val tvName : TextView = findViewById(R.id.tv_name_profile)
                val tvEmail : TextView = findViewById(R.id.tv_email_profile)
                val tvContact : TextView = findViewById(R.id.tv_phone_number)

                tvName.text = user?.name.toString()
                tvEmail.text = user?.email.toString()
                tvContact.text = user?.contact.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        reference.addValueEventListener(menuListener)
    }

    private fun resetPassword(){
        val btnReset : Button = findViewById(R.id.btn_reset_pass)
        btnReset.setOnClickListener {
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }
    }
    private fun backButton(){
        val actionBar = supportActionBar
        actionBar!!.title = "Profile"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        return true
    }
}