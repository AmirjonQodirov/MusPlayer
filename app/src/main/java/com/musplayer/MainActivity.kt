package com.musplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musplayer.utils.GetSongHelper
import com.musplayer.utils.Song
import com.musplayer.utils.SongAdapter
import com.musplayer.utils.SongAdapter.RecycleViewClickListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var songs: ArrayList<Song> = ArrayList()
    private var listener: SongAdapter.RecycleViewClickListener? = null
    private var adapter:SongAdapter? = null

    private fun setOnClickListener(intent_song: ArrayList<Song>) {
        listener = RecycleViewClickListener { view: View?, position: Int, type: Int ->
            when (type) {
                1 -> {
                    Log.w("play " , intent_song[position].title)
                }
                2 -> {
                    Log.w("more " , intent_song[position].uri)
                }
                else -> {
                    Log.w("go_to " , position.toString())
                    val intent = Intent(this, Player::class.java).apply {
                        putExtra("uri", intent_song[position].uri)
                        putExtra("title", intent_song[position].title)
                        putExtra("artist", intent_song[position].artist)
                        putExtra("is_playing" , intent_song[position].is_plying)
                        putExtra("time", intent_song[position].time_play)
                        putExtra("img", intent_song[position].imgUri)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        music_list.isNestedScrollingEnabled = false

        for (s in songs) {
            Log.d("uris: ", s.title)
        }

        songs = GetSongHelper.getSongs();

        //adapter
        setOnClickListener(songs)
        adapter = SongAdapter(songs, listener, this)
        if (songs.isNotEmpty()) {
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            music_list.layoutManager = layoutManager
            music_list.itemAnimator = DefaultItemAnimator()
            music_list.adapter = adapter
        } else {
            // TODO: 24.08.2021 else status
            Log.w("todo", "")
        }


        //hide key_board
        hide_keyboard.setOnTouchListener { v: View, _: MotionEvent? ->
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val focusedView: View? = currentFocus
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                search_edit.clearFocus()
            } else {
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                search_edit.clearFocus()
            }
            true
        }

        //clear search text
        search_edit.onDrawableEndClick {
            search_edit.setText("")
            search_edit.clearFocus()
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    fun EditText.onDrawableEndClick(action: () -> Unit) {
        setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                v as EditText
                val end =
                    if (v.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL)
                        v.left else v.right
                if (event.rawX >= (end - v.compoundPaddingEnd)) {
                    action.invoke()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }
}
