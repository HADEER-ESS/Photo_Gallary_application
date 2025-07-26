package com.hadeer.photogalleryapplication.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hadeer.domain.entity.PhotoModel
import com.hadeer.photogalleryapplication.R
import com.hadeer.photogalleryapplication.databinding.ImageItemBinding

class ImagesAdaptor(val context: Context, val data : List<PhotoModel>):
    RecyclerView.Adapter<ImagesAdaptor.PhotoViewModel>() {

    class PhotoViewModel(private val binding : ImageItemBinding):RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageRes
//        val text = binding.imagTextDesc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val view = ImageItemBinding.inflate(inflater, parent, false)
        return PhotoViewModel(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PhotoViewModel, position: Int) {
        val itemData = data[position]
        val context = holder.itemView.context
        Glide.with(context)
            .load(itemData.url)
            .override(200,100)
            .into(holder.imageView)
        holder.imageView.contentDescription = itemData.alt
    }
}