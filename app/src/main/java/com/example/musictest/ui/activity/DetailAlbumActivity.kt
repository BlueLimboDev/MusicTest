package com.example.musictest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.R
import com.example.musictest.logic.adapter.MusicListAdapter
import com.example.musictest.logic.util.LocalMusicUtils.albumMusicList
import com.example.musictest.logic.util.LocalMusicUtils.albumNow
import com.example.musictest.logic.util.LocalMusicUtils.getAlbumMusic
import kotlinx.android.synthetic.main.activity_detail_album.*

class DetailAlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAlbumMusic(albumNow!!.albumName)

        AlbumDetailImage.setImageURI(albumNow!!.imageId)

        val layoutManager = LinearLayoutManager(this)
        album_allmusic_recyclerView.layoutManager = layoutManager
        val adapter = MusicListAdapter(this,albumMusicList)
        album_allmusic_recyclerView.adapter = adapter

        collapsingToolbar.title = albumNow!!.albumName
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
}