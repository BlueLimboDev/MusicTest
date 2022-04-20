package com.example.musictest.logic.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.musictest.MusicPlayerApplication.Companion.context
import com.example.musictest.logic.util.LocalMusicUtils.mediaPlayer
import com.example.musictest.logic.util.LocalMusicUtils.playAfter

class MusicService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

        mediaPlayer.setOnCompletionListener {
            playAfter(context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}