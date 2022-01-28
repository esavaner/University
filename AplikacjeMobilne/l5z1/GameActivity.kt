package com.example.l5z1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlin.concurrent.thread

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startThread()
        setContentView(R.layout.game_layout)
    }

    private fun startThread() {
        var finished = false
        thread(start = true) {
            while (!finished) {
                if (pointsB > 8 || pointsT > 8) {
                    finished = true
                    if(gameType == 1) {
                        sharedPref = getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPref.edit()
                        if (pointsB > 8) editor.putInt("won", won++)
                        else if (pointsT > 8) editor.putInt("lost", lost++)
                        editor!!.commit()
                        val filename = "pong"
                        val fileContents = "$won\n$lost"
                        openFileOutput(filename, Context.MODE_PRIVATE).use {
                            it.write(fileContents.toByteArray())
                        }
                    }
                    pointsB = 0
                    pointsT = 0
                    val myIntent = Intent()
                    myIntent.putExtra("key",1)
                    setResult(Activity.RESULT_OK, myIntent)
                    finish()
                }
            }
        }
    }
}
