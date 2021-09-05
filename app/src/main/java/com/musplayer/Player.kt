package com.musplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*


class Player : AppCompatActivity() {

    private var handler: Handler = Handler()
    private lateinit var runnable:Runnable;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        //vars
        val uri: String? = intent.getStringExtra("uri")

        //media player
        val mp = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(uri)
            prepare()
        }

        //seek_bar
        val seconds: Int = mp.duration / 1000

        time_end.text = getOwnFormat(seconds)
        seek_bar.progress = mp.currentPosition
        time_now.text = getOwnFormat(mp.currentPosition / 1000)
        seek_bar.max = seconds

        seek_bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    time_now.text = getOwnFormat(progress)
                    mp.seekTo(progress * 1000)
                }
            }
        })


        runnable = Runnable {
            seek_bar.progress = mp.currentPosition / 1000

            time_end.text = "~~"
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)


        //title & artist
        song_name.text = intent.getStringExtra("title")
        song_author.text = intent.getStringExtra("artist")
        var is_playing = false

        //

        //play_btn
        play.setOnClickListener {
            if (!is_playing) {
                play.setImageResource(R.drawable.ic_pause)
                mp.start()
            } else {
                play.setImageResource(R.drawable.ic_play)
                mp.pause()
            }
            is_playing = !is_playing

        }
    }

    fun dead(view: View) {
        finish()
    }

    fun getOwnFormat(seconds: Int): String {
        return "${(seconds / 60).toString().padStart(2, '0')}:${
            (seconds % 60).toString().padStart(2, '0')
        }"
    }


}