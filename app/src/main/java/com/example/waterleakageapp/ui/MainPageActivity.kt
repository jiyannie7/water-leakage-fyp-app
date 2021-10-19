package com.example.waterleakageapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.adapters.ImageAdapter
import com.example.waterleakageapp.data.Image
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainPageActivity : AppCompatActivity() {
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerView : RecyclerView
    private lateinit var list : ArrayList<Image>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        list = ArrayList()
        adapter = ImageAdapter(this,list)
        recyclerView = findViewById(R.id.rv_news)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        setUpFab()
        reportForm()
        reportStatus()
        setAnnouncement()
    }

    private fun setAnnouncement(){

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("News")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (data in snapshot.children){
                        val image = data.getValue(Image::class.java)
                        list.add(image!!)
                    }
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainPageActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun setUpFab(){
        val fab :FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this,ChatbotActivity::class.java))
        }

    }
    private fun reportStatus(){
        val cvStatus : CardView = findViewById(R.id.cv_status)
        cvStatus.setOnClickListener {
            startActivity(Intent(this,StatusActivity::class.java))
        }
    }
    private fun reportForm(){
        val cvForm : CardView = findViewById(R.id.cv_form)
        cvForm.setOnClickListener {
            startActivity(Intent(this,ReportFormActivity::class.java))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.profile -> {
                startActivity(Intent(this,ProfileActivity::class.java))
                return true
            }
            R.id.logout -> {
                Firebase.auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                onBackPressed()
                return true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}