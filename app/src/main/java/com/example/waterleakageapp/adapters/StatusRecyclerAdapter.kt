 package com.example.waterleakageapp.adapters
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Forms
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class StatusRecyclerAdapter(var ctx : Context,private val rList : ArrayList<Forms>):
    RecyclerView.Adapter<StatusRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDate: TextView = view.findViewById(R.id.tv_status_date)
        var tvDivision: TextView = view.findViewById(R.id.tv_status_division)
        var tvDistrict: TextView = view.findViewById(R.id.tv_status_district)
        var tvCategory: TextView = view.findViewById(R.id.tv_status_cat)
        var tvRemark: TextView = view.findViewById(R.id.tv_status_remark)
        var tvStatus : TextView = view.findViewById(R.id.tv_pending)
        var btnDelete: Button = view.findViewById(R.id.btn_delete)
        init {

            btnDelete.setOnClickListener {
                val formList = rList[adapterPosition]
                deleteData(formList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder((LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_status, parent, false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDate.text = rList[position].date
        holder.tvDivision.text = rList[position].division
        holder.tvDistrict.text = rList[position].district
        holder.tvCategory.text = rList[position].category
        holder.tvRemark.text = rList[position].message
        holder.tvStatus.text = rList[position].status
    }

    override fun getItemCount(): Int {
        return rList.size
    }

    fun deleteData(formList: Forms) {
        val builder = AlertDialog.Builder(ctx)
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.edit_status, null)

        builder.setTitle("DELETE FORM")
        builder.setView(view)
        builder.setPositiveButton("Delete") { _, _ ->

            val userID = Firebase.auth.currentUser!!
            val dbStatus =
                FirebaseDatabase.getInstance().getReference("Users").child(userID.uid).child("Forms")
            dbStatus.child(formList.fId).removeValue()
            notifyDataSetChanged()
            Toast.makeText(ctx, "Report deleted successfully", Toast.LENGTH_SHORT).show()

        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }
        val alert = builder.create()
        alert.show()
    }
}
