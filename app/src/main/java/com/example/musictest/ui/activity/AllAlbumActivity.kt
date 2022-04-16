package com.example.musictest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.R
import com.example.musictest.logic.adapter.AlbumAdapter
import com.example.musictest.logic.util.LocalMusicUtils
import com.example.musictest.logic.util.LocalMusicUtils.albumMusicList
import com.example.musictest.logic.util.LocalMusicUtils.allAlbumList
import kotlinx.android.synthetic.main.activity_all_album.*
import kotlinx.android.synthetic.main.activity_detail_album.*

class AllAlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_album)

        val layoutManager = LinearLayoutManager(this)
        all_album_recyclerView.layoutManager = layoutManager
        val adapter = AlbumAdapter(this,allAlbumList)
        all_album_recyclerView.adapter = adapter

    }

    override fun onPause() {
        super.onPause()
        albumMusicList.clear()
    }
}