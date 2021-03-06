package com.example.musictest.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictest.R
import com.example.musictest.logic.adapter.AlbumShowAdapter
import com.example.musictest.logic.adapter.MusicListAdapter
import com.example.musictest.logic.adapter.PlayerShowAdapter
import com.example.musictest.logic.service.MusicService
import com.example.musictest.logic.util.LocalMusicUtils.allAlbumList
import com.example.musictest.logic.util.LocalMusicUtils.allMusicList
import com.example.musictest.logic.util.LocalMusicUtils.allPlayerList
import com.example.musictest.logic.util.LocalMusicUtils.getAllMusic
import com.example.musictest.logic.util.LocalMusicUtils.intNow
import com.example.musictest.logic.util.LocalMusicUtils.playingListRecord
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_playing_list.*

class MainActivity : AppCompatActivity() {

    val musicAdapter = MusicListAdapter(this,allMusicList)
    var playingAdapter = MusicListAdapter(this, playingListRecord)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(playingListtoolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val intent = Intent(this,MusicService::class.java)
        startService(intent)

        /**
         * 授权 / 检查授权
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            iniData()
        }

        player_click.setOnClickListener {
            val intent = Intent(this,AllPlayerActivity::class.java)
            startActivity(intent)
        }
        album_click.setOnClickListener {
            val intent = Intent(this,AllAlbumActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        /**
         * 如果点击了播放内容，则刷新播放列表
         */
        showPlayingList()
        sume_music.text = playingListRecord.size.toString()

        if (playingListRecord.size != 0){
            playingAdapter.musicList[intNow!!].playing = true
            playingAdapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        super.onPause()

        /**
         * 如果为主播放列表，则刷新全部音乐列表
         */
        if (musicAdapter.musicList.size == playingListRecord.size){
            for (i in 0..musicAdapter.musicList.size-1){
                musicAdapter.musicList[i].playing = false
            }
            musicAdapter.musicList[intNow!!].playing = true
        }else{
            for (i in 0..musicAdapter.musicList.size-1){
                musicAdapter.musicList[i].playing = false
            }
        }
        musicAdapter.notifyDataSetChanged()


    }

    /**
     * 权限监控
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    getAllMusic(this)
                    iniData()
                }else{
                    Toast.makeText(this,"你已拒绝本次授权", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }


    /**
     * 显示RecyclerView
     */
    fun iniData(){
        getAllMusic(this)
        showMusic()
        showAlbum()
        showPlayer()
    }
    fun showMusic(){
        val layoutManager = LinearLayoutManager(this)
        allmusic_recyclerView.layoutManager = layoutManager
        allmusic_recyclerView.adapter = musicAdapter
    }
    fun showAlbum(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        album_recyclerView.layoutManager = layoutManager
        val adapter = AlbumShowAdapter(allAlbumList)
        album_recyclerView.adapter = adapter
    }
    fun showPlayer(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        player_recyclerView.layoutManager = layoutManager
        val adapter = PlayerShowAdapter(allPlayerList)
        player_recyclerView.adapter = adapter
    }
    fun showPlayingList(){
        val layoutManager = LinearLayoutManager(this)
        playinglist_recyclerView.layoutManager = layoutManager
        playinglist_recyclerView.adapter = playingAdapter
    }
}