package com.example.musictest

import android.app.Application
import android.content.Context

class MusicPlayerApplication: Application(){
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}