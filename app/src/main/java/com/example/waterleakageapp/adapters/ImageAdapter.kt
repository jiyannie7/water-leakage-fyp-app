package com.example.waterleakageapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Image
import com.example.waterleakageapp.databinding.ItemNewsBinding
import com.example.waterleakageapp.ui.NewsDetailsActivity

class ImageAdapter(
    private val context: Context, var images : ArrayList<Image>
    ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(var v : ItemNewsBinding) : RecyclerView.ViewHolder(v.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemNewsBinding>(
            inflater, R.layout.item_news,parent,false
        )
        return ImageViewHolder(v)
    }
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       val newsList = images[position]
        holder.v.isItem = images[position]
        holder.v.root.setOnClickListener {
            val imageTitle = newsList.imageTitle
            val imageDesc = newsList.imageDesc
            val imageSrc = newsList.imageSrc
            val intent = Intent(context,NewsDetailsActivity::class.java)
            intent.putExtra("imageSrc",imageSrc)
            intent.putExtra("imageTitle",imageTitle)
            intent.putExtra("imageDesc",imageDesc)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return images.size
    }
}
