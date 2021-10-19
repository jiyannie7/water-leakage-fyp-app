package com.example.waterleakageapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.adapters.StatusRecyclerAdapter
import com.example.waterleakageapp.data.Forms
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class StatusActivity : AppCompatActivity() {

    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        val userID = Firebase.auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users").child(userID.uid).child("Forms")

        val list = ArrayList<Forms>()
        recyclerView = findViewById(R.id.rv_status)
        recyclerView.layoutManager = LinearLayoutManager(this)
       val adapter = StatusRecyclerAdapter(this,list)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        backButton()
        getData()

    }
    private fun getData(){
        reference.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Forms>()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val model = data.getValue(Forms::class.java)
                        list.add(model as Forms)
                        val adapter = StatusRecyclerAdapter(this@StatusActivity, list)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            } })
    }
    private fun backButton(){
        val actionBar = supportActionBar
        actionBar!!.title = "Report Status"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}