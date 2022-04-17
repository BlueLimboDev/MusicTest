package com.example.musictest.logic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musictest.logic.model.Music
import com.example.musictest.R
import com.example.musictest.logic.util.LocalMusicUtils.musicNow
import com.example.musictest.ui.activity.DtailMusicActivity

class MusicListAdapter(val context: Context, val musicList: MutableList<Music>): RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val musicName: TextView = view.findViewById(R.id.musicName)
        val musicEr: TextView = view.findViewById(R.id.musicEr)
        val musicPlaying: ImageView = view.findViewById(R.id.musicPlaying)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            musicNow = musicList[position]
            val intent = Intent(context, DtailMusicActivity::class.java)
            context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        /**
         * 通过条件控制recyclerView是否显示播放按钮
         */
        holder.musicName.text = music.name
        holder.musicEr.text = music.player
        if (music.playing){
            holder.musicPlaying.visibility = View.VISIBLE
        }else{
            holder.musicPlaying.visibility = View.GONE
        }
    }

    override fun getItemCount() = musicList.size
}