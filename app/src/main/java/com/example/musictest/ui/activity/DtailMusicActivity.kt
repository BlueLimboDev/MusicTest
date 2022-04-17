package com.example.musictest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musictest.R
import com.example.musictest.logic.util.LocalMusicUtils.musicNow
import kotlinx.android.synthetic.main.activity_dtail_music.*

class DtailMusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dtail_music)

        musicDetailImage.setImageURI(musicNow!!.imageId)
        music_detail_title.text = musicNow!!.name
    }
}