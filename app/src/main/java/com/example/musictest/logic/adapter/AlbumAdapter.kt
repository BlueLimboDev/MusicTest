package com.example.musictest.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musictest.R
import com.example.musictest.logic.model.Album

class AlbumAdapter(val albumList: MutableList<Album>): RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val albumImg: ImageView = view.findViewById(R.id.all_album_img)
        val albumName:TextView = view.findViewById(R.id.all_album_name)
        val albumPlayer:TextView = view.findViewById(R.id.all_album_player)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_album,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        holder.albumImg.setImageURI(album.imageId)
        holder.albumName.text = album.albumName
        holder.albumPlayer.text = album.albumPlayer
    }

    override fun getItemCount() = albumList.size
}