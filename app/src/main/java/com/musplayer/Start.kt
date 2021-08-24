package com.musplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.musplayer.utils.GetSongHelper


class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val task = LoadData(this);
        task.execute(0)


    }

    @SuppressLint("StaticFieldLeak")
    class LoadData(var context: Context) : AsyncTask<Int?, Int?, String>() {

        override fun doInBackground(vararg params: Int?): String {
            GetSongHelper.getAllAudioFromDevice(context)
            return "Done!"
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            if (GetSongHelper.isUris_loaded()) {
                Handler().postDelayed({
                    val i = Intent(context, MainActivity::class.java)
                    context.startActivity(i)
                    (context as Activity).finish()
                    }, 2000)
            } else {
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                val task = LoadData(context)
                task.execute(0)
            }
        }
    }

}