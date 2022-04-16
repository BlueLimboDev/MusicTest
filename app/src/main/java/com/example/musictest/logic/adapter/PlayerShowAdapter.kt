package com.example.musictest.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musictest.logic.model.Player
import com.example.musictest.R
import de.hdodenhof.circleimageview.CircleImageView

class PlayerShowAdapter(val playerList: MutableList<Player>): RecyclerView.Adapter<PlayerShowAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val playerImage: CircleImageView = view.findViewById(R.id.playerImage)
        val playerName: TextView = view.findViewById(R.id.playerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = playerList[position]
        /**
         * 通过条件控制recyclerView是否显示播放按钮
         */
        holder.playerImage.setImageURI(player.imageId)
        holder.playerName.text = player.name
    }

    override fun getItemCount() = playerList.size
}