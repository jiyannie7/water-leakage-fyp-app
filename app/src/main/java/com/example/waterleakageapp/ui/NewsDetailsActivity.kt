package com.example.waterleakageapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Image

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val imgSrc = findViewById<ImageView>(R.id.iv_details)
        val imgTitle = findViewById<TextView>(R.id.tv_title)
        val imgDesc = findViewById<TextView>(R.id.tv_details)

        val intent = intent
        val imageTitle = intent.getStringExtra("imageTitle")
        val imageDesc = intent.getStringExtra("imageDesc")
        val imageImg = intent.getStringExtra("imageSrc")

        imgTitle.text = imageTitle
        imgDesc.text = imageDesc
        imgSrc.loadImage(imageImg, getProgressDrawable(this))

        backButton()
    }
    private fun backButton(){
        val actionBar = supportActionBar
        actionBar!!.title = "Announcements"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        return true
    }
}