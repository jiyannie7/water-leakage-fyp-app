package com.example.waterleakageapp.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Forms
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class ReportFormActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val id = "channel_id_example_0"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val userID = Firebase.auth.currentUser!!
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users").child(userID.uid).child("Forms")

        datePicker()
        createNotificationChannel()
        menuCategory()
        divisionCategory()
        btnSubmit()
        backButton()
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id,name,importance).apply {
                description = descriptionText

            }
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.lightColor = Color.BLUE
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){

        val intent = Intent(this,MainPageActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val bitmapLarge = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.ic_baseline_notifications_24)

        val builder = NotificationCompat.Builder(this,id)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Report form submission")
            .setContentText("Description")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(bitmapLarge)
            .setStyle((NotificationCompat.BigTextStyle().bigText("The form is successfully submitted.")))
            .setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun datePicker(){
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val txtDate : EditText = findViewById(R.id.et_date)
        txtDate.setOnClickListener {

            val picker = DatePickerDialog(this, { _, year, month, dayOfMonth ->

                txtDate.setText("${dayOfMonth}-${month+1}-${year}")
            },year,month,day)

            picker.show()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun menuCategory(){
        val txtCategory : EditText = findViewById(R.id.et_category)
        txtCategory.setOnClickListener {
            val popUp = PopupMenu(this,txtCategory)
            popUp.setOnMenuItemClickListener {item ->
                when(item.itemId){
                    R.id.txt_category_1 ->
                    {
                        txtCategory.setText("Water leaks")
                        true
                    }
                    R.id.txt_category_2 ->
                    {
                        txtCategory.setText("Pipe burst")
                        true
                    }
                    R.id.txt_category_3 ->
                    {
                        txtCategory.setText("Low pressure water")
                        true
                    }
                    R.id.txt_category_4 ->
                    {
                        txtCategory.setText("Water cleaning")
                        true
                    }
                    R.id.txt_category_5 ->
                    {
                        txtCategory.setText("Others")
                        true
                    }
                    else -> false
                }

            }
            popUp.inflate(R.menu.menu_form_category)
            popUp.show()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun divisionCategory(){
        val txtDivision : EditText = findViewById(R.id.et_location_division)
        txtDivision.setOnClickListener {
            val popUp = PopupMenu(this, txtDivision)
            popUp.setOnMenuItemClickListener {item ->
                when(item.itemId){
                    R.id.betong ->
                    {

                        txtDivision.setText("Betong")
                        true
                    }
                    R.id.bintulu ->
                    {
                        txtDivision.setText("Bintulu")
                        true
                    }
                    R.id.kapit->
                    {
                        txtDivision.setText("Kapit")
                        true
                    }
                    R.id.kuching ->
                    {
                        txtDivision.setText("Kuching")
                        true
                    }
                    R.id.limbang ->
                    {
                        txtDivision.setText("Limbang")
                        true
                    }
                    R.id.miri->
                    {
                        txtDivision.setText("Miri")
                        true
                    }
                    R.id.mukah->
                    {
                        txtDivision.setText("Mukah")
                        true
                    }
                    R.id.samarahan->
                    {
                        txtDivision.setText("Samarahan")
                        true
                    }
                    R.id.sarikei-> {
                        txtDivision.setText("Sarikei")
                        true
                    }
                    R.id.serian->
                    {
                        txtDivision.setText("Serian")
                        true
                    }
                    R.id.sibu->
                    {
                        txtDivision.setText("Sibu")
                        true
                    }
                    R.id.sri_aman->
                    {
                        txtDivision.setText("Sri Aman")
                        true
                    }
                    else -> false
                }

            }
            popUp.inflate(R.menu.menu_division_category)
            popUp.show()
        }
    }
    private fun btnSubmit(){

        val btnSubmit : Button = findViewById(R.id.btn_form)
        btnSubmit.setOnClickListener {
        val etDate : EditText = findViewById(R.id.et_date)
        val etCategory : EditText = findViewById(R.id.et_category)
        val etLocationDivision : AutoCompleteTextView = findViewById(R.id.et_location_division)
            val etLocationDistrict : EditText = findViewById(R.id.et_location_district)

            if(etDate.text.toString().isEmpty() || etCategory.text.toString().isEmpty() ||etLocationDivision.text.toString().isEmpty() || etLocationDistrict.text.toString().isEmpty()){

                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }

       else if(etDate.text.toString().isEmpty()){
            etDate.error = "Please enter date"
            etDate.requestFocus()
            return@setOnClickListener
        }
        else if(etCategory.text.toString().isEmpty()){
            etCategory.error = "Please choose category"
            etCategory.requestFocus()
            return@setOnClickListener
        }
          else if(etLocationDivision.text.toString().isEmpty()){
               etLocationDivision.error = "Please provide division"
               etLocationDivision.requestFocus()
               return@setOnClickListener
           }
            else if (etLocationDistrict.text.toString().isEmpty()){
                etLocationDistrict.error = "Please provide district"
                etLocationDistrict.requestFocus()
                return@setOnClickListener
            }
            else {
               sendFormData()
               sendNotification()
               Toast.makeText(applicationContext, "Form created successfully", Toast.LENGTH_SHORT)
                   .show()
               startActivity(Intent(this, MainPageActivity::class.java))
           }
        }
    }
    private fun sendFormData(){
        val txtFormDate = findViewById<EditText>(R.id.et_date)
        val txtFormCategory = findViewById<EditText>(R.id.et_category)
        val txtLocationDivision = findViewById<AutoCompleteTextView>(R.id.et_location_division)
        val txtLocationDistrict = findViewById<EditText>(R.id.et_location_district)
        val txtRemark = findViewById<EditText>(R.id.et_remark)
        val date = txtFormDate.text.toString().trim()
        val category = txtFormCategory.text.toString().trim()
        val district = txtLocationDistrict.text.toString().trim()
        val division = txtLocationDivision.text.toString().trim()
        val message =  txtRemark.text.toString().trim()
        val formId = reference.push().key

        //send form data to firebase
        reference.child(formId!!).setValue(Forms(formId,date,category,division,district,message)).addOnCanceledListener {
            Toast.makeText(this,"Report saved successfully",Toast.LENGTH_SHORT).show()
        }

    }
    private fun backButton(){
        val actionBar = supportActionBar
        actionBar!!.title = "Report Form"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        return true
    }
}