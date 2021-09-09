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
import com.musplayer.utils.GlobalPlayer
import kotlinx.android.synthetic.main.activity_player.*


class Player : AppCompatActivity() {

    private var handler: Handler = Handler()
    private lateinit var runnable: Runnable;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        //get uri
        val uri: String? = intent.getStringExtra("uri")

        //media player init
        if (!GlobalPlayer.isPlayingNow()) {
            GlobalPlayer.mediaPlayerInit(uri)
        }else{
            play.setImageResource(R.drawable.ic_pause)
        }

        //seek_bar
        //timing
        val seconds: Int = GlobalPlayer.durationSong() / 1000
        seek_bar.max = seconds
        time_now.text = getOwnFormat(GlobalPlayer.currentPositionSong() / 1000)
        time_end.text = getOwnFormat(seconds)
        seek_bar.progress = GlobalPlayer.currentPositionSong() / 1000
        seek_bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    time_now.text = getOwnFormat(progress)
                    GlobalPlayer.seekToSong(progress * 1000)
                }
            }
        })
        runnable = Runnable {
            seek_bar.progress = GlobalPlayer.currentPositionSong() / 1000
            time_now.text = getOwnFormat(seek_bar.progress)
            handler.postDelayed(runnable, 1000)
            if(time_now.text.equals(time_end.text)){
                time_now.text = getOwnFormat(0);
                seek_bar.progress = 0
                play.setImageResource(R.drawable.ic_play)
            }
        }
        handler.postDelayed(runnable, 1000)

        //title & artist
        song_name.text = intent.getStringExtra("title")
        song_author.text = intent.getStringExtra("artist")

        //play_btn
        play.setOnClickListener {
            if (!GlobalPlayer.isPlayingNow()) {
                play.setImageResource(R.drawable.ic_pause)
                GlobalPlayer.playSong()
            } else {
                play.setImageResource(R.drawable.ic_play)
                GlobalPlayer.pauseSong()
            }
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