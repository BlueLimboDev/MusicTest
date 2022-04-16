package com.example.musictest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlbumListAdapter(val albumList: MutableList<Album>): RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val albumImg: ImageView = view.findViewById(R.id.albumImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        /**
         * 通过条件控制recyclerView是否显示播放按钮
         */
        holder.albumImg.setImageURI(album.imageId)
    }

    override fun getItemCount() = albumList.size
}