package com.example.musictest.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.musictest.logic.model.Music
import com.example.musictest.logic.util.LocalMusicUtils
import com.example.musictest.logic.util.LocalMusicUtils.allAlbumList
import com.example.musictest.logic.util.LocalMusicUtils.allMusicList
import com.example.musictest.logic.util.LocalMusicUtils.allPlayerList
import com.example.musictest.logic.util.LocalMusicUtils.getAllMusic
import com.example.musictest.logic.util.LocalMusicUtils.playingListRecord
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_playing_list.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(playingListtoolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.playing)
        }

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
        showPlayingList()
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
        show()
        showAlbum()
        showPlayer()
    }
    fun show(){
        val layoutManager = LinearLayoutManager(this)
        allmusic_recyclerView.layoutManager = layoutManager
        val adapter = MusicListAdapter(this,allMusicList)
        allmusic_recyclerView.adapter = adapter
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
        val adapter = MusicListAdapter(this, playingListRecord)
        playinglist_recyclerView.adapter = adapter
    }
}