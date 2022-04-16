package com.example.musictest

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
    var playing: Boolean
)