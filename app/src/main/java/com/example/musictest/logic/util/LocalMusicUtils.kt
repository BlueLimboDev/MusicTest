package com.example.musictest.logic.util

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.musictest.logic.model.Album
import com.example.musictest.logic.model.Music
import com.example.musictest.logic.model.Player

object LocalMusicUtils {
    val allMusicList = mutableListOf<Music>()
    val allAlbumList = mutableListOf<Album>()
    val allPlayerList = mutableListOf<Player>()

    /**
     * 获取全部音乐，返回一个ArrayList<Music>()对象
     */
    fun getAllMusic(context:Context){

        val alList = mutableListOf<Album>()
        val plList = mutableListOf<Player>()
        /**
         * 从本地数据库中获取外部存储的音乐文件、专辑、歌手信息
         */
        val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null){
            while (cursor.moveToNext()){
                var name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                var player = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id)
                val imageId = getArt(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))
                val album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                val pair = splitNameSinger(name, player)
                name = pair.first
                player = pair.second
                //把歌曲名字和歌手切割开,如果文件大于8000KB，则为音乐文件
                if (size > 1000 * 8000) {
                    //将音乐文件名分割为歌曲名和歌手名
                    val music = Music(imageId,name,player,id,uri,album,false)
                    allMusicList.add(music)

                    val album = Album(imageId,album,player)
                    alList.add(album)

                    val pla = Player(imageId,player)
                    plList.add(pla)
                }
            }
            cursor.close()
        }

        var temp = "测试"
        for (i in 0..alList.size-1){
            if (alList[i].albumName != temp){
                allAlbumList.add(alList[i])
                temp = alList[i].albumName
            }
        }
        for (i in 0..plList.size-1){
            if (plList[i].name != temp){
                allPlayerList.add(plList[i])
                temp = plList[i].name
            }
        }
    }

    /**
     * 音乐名称转换
     */
    fun splitNameSinger(name: String?, singer: String?): Pair<String?, String?> {
        var name1 = name
        var singer1 = singer
        if (name1!!.contains(" - ")) {
            val str = name1.split(" - ".toRegex()).toTypedArray()
            if (str.size == 2) {
                if (singer1 == "<unknown>"){
                    singer1 = str[0].trim()
                }
                name1 = str[1].trim()
            }
        }
        return Pair(name1, singer1)
    }
    /**
     * 获取封面图
     */
    fun getArt(album_id:Long): Uri {
        val artworkUri = Uri.parse("content://media/external/audio/albumart")
        return ContentUris.withAppendedId(artworkUri, album_id)
    }

}