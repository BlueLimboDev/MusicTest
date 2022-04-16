package com.example.musictest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.R
import com.example.musictest.logic.adapter.PlayerAdapter
import com.example.musictest.logic.util.LocalMusicUtils.allPlayerList
import com.example.musictest.logic.util.LocalMusicUtils.playerAlbumList
import com.example.musictest.logic.util.LocalMusicUtils.playerMusicList
import kotlinx.android.synthetic.main.activity_all_player.*

class AllPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_player)

        val layoutManager = LinearLayoutManager(this)
        all_player_recyclerView.layoutManager = layoutManager
        val adapter = PlayerAdapter(this,allPlayerList)
        all_player_recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        playerMusicList.clear()
        playerAlbumList.clear()
    }
}