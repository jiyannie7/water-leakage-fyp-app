package com.example.waterleakageapp.ui
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.adapters.MessagingAdapter
import com.example.waterleakageapp.data.Message
import com.example.waterleakageapp.utils.BotResponse

import com.example.waterleakageapp.utils.Constants.RECEIVE_ID
import com.example.waterleakageapp.utils.Constants.SEND_ID
import com.example.waterleakageapp.utils.Time
import kotlinx.coroutines.*

class ChatbotActivity : AppCompatActivity() {

    private lateinit var adapter : MessagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        recyclerView()
        clickEvents()
        backButton()
        customMessage("Hello! I am Ari. Try asking me by typing one of the keywords below")
        keyWord("About us\nComplaint\nService Type\nEnquiry\nOffice Location")
    }
    private fun backButton(){
        val actionBar = supportActionBar
        actionBar!!.title = "Chat"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun clickEvents(){
        val btnSend : Button = findViewById(R.id.btn_send)
        val etMessage : EditText = findViewById(R.id.et_message)
        btnSend.setOnClickListener {
            sendMessage()
        }
        etMessage.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
                    rvMessages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }
    private fun recyclerView(){
        val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
        adapter = MessagingAdapter()
        rvMessages.adapter = adapter
        rvMessages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
        val etMessage : EditText = findViewById(R.id.et_message)
        val message = etMessage.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()){

            etMessage.setText("")
            closeKeyboard()
            adapter.insertMessage(Message(message,SEND_ID,timeStamp))
            rvMessages.scrollToPosition(adapter.itemCount - 1)
            botResponse(message)
        }
    }

    private fun botResponse(message: String){
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){

                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECEIVE_ID,timeStamp))
                val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
                rvMessages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
                rvMessages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    private fun customMessage(message:String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timestamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timestamp))
                val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
                rvMessages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun keyWord(keywords:String){
        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main){
                val timestamp = Time.timeStamp()
                adapter.insertMessage(Message(keywords,RECEIVE_ID,timestamp))
                val rvMessages : RecyclerView = findViewById(R.id.rv_messages)
                rvMessages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    private fun closeKeyboard(){
        val view = this.currentFocus
        if(view!=null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}