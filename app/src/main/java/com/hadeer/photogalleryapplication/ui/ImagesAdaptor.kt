package com.hadeer.photogalleryapplication.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hadeer.domain.entity.PhotoModel
import com.hadeer.photogalleryapplication.R
import com.hadeer.photogalleryapplication.databinding.ImageItemBinding

class ImagesAdaptor():
//    RecyclerView.Adapter<ImagesAdaptor.PhotoViewModel>()  => before using DiffUtil
//ListAdaptor will take care of you List modifications
ListAdapter<PhotoModel , ImagesAdaptor.PhotoViewModel>(PhotosListDiffCallback())
{

    class PhotoViewModel(binding : ImageItemBinding):RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageRes
        //the perfect place for OnClick Handling to create ClickListener to RecyclerView
//        val text = binding.imagTextDesc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val view = ImageItemBinding.inflate(inflater, parent, false)
        return PhotoViewModel(view)
    }

    override fun onBindViewHolder(holder: PhotoViewModel, position: Int) {
        val itemData = getItem(position)
        val context = holder.itemView.context
        Glide.with(context)
            .load(itemData.url)
            .override(200,100)
            .into(holder.imageView)
        holder.imageView.contentDescription = itemData.alt
    }
}

class PhotosListDiffCallback : DiffUtil.ItemCallback<PhotoModel>(){
    override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem == newItem
    }
}