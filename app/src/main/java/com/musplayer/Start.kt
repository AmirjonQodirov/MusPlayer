package com.musplayer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.musplayer.utils.GetSongHelper


class Start : AppCompatActivity() {

    private val permissionRequest: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        getPermission()

        val task = LoadData(this);
        task.execute(0)


    }

    private fun getPermission() {
        //permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    permissionRequest
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    permissionRequest
                )
            }
        } else {
            //
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequest -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                return
            }
            else -> {
            }
        }
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