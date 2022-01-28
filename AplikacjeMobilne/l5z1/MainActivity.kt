package com.example.l5z1

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when (view) {
            imageView -> gameType = 1
            imageView2 -> gameType = 2
        }
        val myIntent = Intent(this, GameActivity::class.java)
        myIntent.putExtra("key", 1)
        startActivityForResult(myIntent, 123)
    }

    override fun onResume() {
        sharedPref = getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE)
        won = sharedPref.getInt("won", 0)
        lost = sharedPref.getInt("lost", 0)
        textView.text = "Wygrane: $won"
        textView2.text = "Przegrane: $lost"
        super.onResume()
    }
}