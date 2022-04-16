package com.example.musictest.logic.model

import android.net.Uri

/**
 * 定义实体类
 */
data class Music(
    val imageId: Uri,
    val name: String,
    val player: String,
    val id: Long,
    val uri: Uri,
    val albumname:String,
    var playing: Boolean
)