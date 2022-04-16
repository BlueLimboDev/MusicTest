package com.example.musictest.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musictest.R
import com.example.musictest.logic.model.Player
import de.hdodenhof.circleimageview.CircleImageView

class PlayerAdapter(val playerList: MutableList<Player>): RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val plyerImg: CircleImageView = view.findViewById(R.id.all_player_img)
        val playerName: TextView = view.findViewById(R.id.all_player_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_player,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = playerList[position]
        holder.plyerImg.setImageURI(player.imageId)
        holder.playerName.text = player.name
    }

    override fun getItemCount() = playerList.size
}