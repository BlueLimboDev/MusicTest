package com.example.musictest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.R
import com.example.musictest.logic.adapter.AlbumAdapter
import com.example.musictest.logic.adapter.MusicListAdapter
import com.example.musictest.logic.util.LocalMusicUtils.getPlayerAlbum
import com.example.musictest.logic.util.LocalMusicUtils.getPlayerMusic
import com.example.musictest.logic.util.LocalMusicUtils.playerAlbumList
import com.example.musictest.logic.util.LocalMusicUtils.playerMusicList
import com.example.musictest.logic.util.LocalMusicUtils.playerNow
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        setSupportActionBar(player_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        player_AlbumDetailImage.setImageURI(playerNow!!.imageId)
        player_collapsingToolbar.title = playerNow!!.name

        getPlayerMusic(playerNow!!.name)
        getPlayerAlbum(playerNow!!.albumName)

        detail_player_allmusic.setOnClickListener {
            showMusic()
        }
        detail_player_album.setOnClickListener {
            showAlbum()
        }

        showMusic()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun showMusic(){
        val layoutManager = LinearLayoutManager(this)
        player_detail_recyclerView.layoutManager = layoutManager
        val adapter = MusicListAdapter(this,playerMusicList)
        player_detail_recyclerView.adapter = adapter
    }
    fun showAlbum(){
        val layoutManager = LinearLayoutManager(this)
        player_detail_recyclerView.layoutManager = layoutManager
        val adapter = AlbumAdapter(this,playerAlbumList)
        player_detail_recyclerView.adapter = adapter
    }
}