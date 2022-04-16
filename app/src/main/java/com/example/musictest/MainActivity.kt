package com.example.musictest

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 授权 / 检查授权
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            show()
            showAlbum()
            showPlayer()
        }
    }

    /**
     * 权限监控
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    show()
                    showAlbum()
                    showPlayer()
                }else{
                    Toast.makeText(this,"你已拒绝本次授权", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 获取全部音乐，返回一个ArrayList<Music>()对象
     */
    fun getAllMusic():MutableList<Music>{
        val list = mutableListOf<Music>()
        /**
         * 从本地数据库中获取外部存储的音乐文件信息
         */
        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null){
            while (cursor.moveToNext()){
                var name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                var player = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id)
                val imageId = getArt(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))

                //把歌曲名字和歌手切割开,如果文件大于8000KB，则为音乐文件
                if (size > 1000 * 8000) {
                    //将音乐文件名分割为歌曲名和歌手名
                    val pair = splitNameSinger(name, player)
                    name = pair.first
                    player = pair.second
                    val music = Music(imageId,name,player,id,uri,false)
                    list.add(music)
                }
            }
            cursor.close()
        }
        return list
    }

    /**
     * 获取专辑列表
     */
    fun getAlbum():MutableList<Album>{
        val list = mutableListOf<Album>()
        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null){
            while (cursor.moveToNext()){
                var name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                var player = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                val imageId = getArt(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))
                val album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                //把歌曲名字和歌手切割开,如果文件大于8000KB，则为音乐文件
                if (size > 1000 * 8000) {
                    //将音乐文件名分割为歌曲名和歌手名
                    val pair = splitNameSinger(name, player)
                    name = pair.first
                    player = pair.second
                    val album = Album(imageId,album,player)
                    list.add(album)
                }
            }
            cursor.close()
        }
        return list
    }

    /**
     * 获取专辑列表
     */
    fun getPlayer():MutableList<Player>{
        val list = mutableListOf<Player>()
        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null){
            while (cursor.moveToNext()){
                var name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                var player = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                val imageId = getArt(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))
                val album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                //把歌曲名字和歌手切割开,如果文件大于8000KB，则为音乐文件
                if (size > 1000 * 8000) {
                    //将音乐文件名分割为歌曲名和歌手名
                    val pair = splitNameSinger(name, player)
                    name = pair.first
                    player = pair.second
                    val pla = Player(imageId,player)
                    list.add(pla)
                }
            }
            cursor.close()
        }
        return list
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

    /**
     * 显示RecyclerView
     */
    fun show(){
        val layoutManager = LinearLayoutManager(this)
        allmusic_recyclerView.layoutManager = layoutManager
        val adapter = MusicListAdapter(getAllMusic())
        allmusic_recyclerView.adapter = adapter
    }

    fun showAlbum(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        album_recyclerView.layoutManager = layoutManager
        val adapter = AlbumListAdapter(getAlbum())
        album_recyclerView.adapter = adapter
    }

    fun showPlayer(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        player_recyclerView.layoutManager = layoutManager
        val adapter = PlayerListAdapter(getPlayer())
        player_recyclerView.adapter = adapter
    }
}