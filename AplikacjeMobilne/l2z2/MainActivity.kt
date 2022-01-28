package com.example.l2z2


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var won: Int? = 0
    private var lost: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            var wonE = data?.getIntExtra("wygrane", 123)
            var lostE = data?.getIntExtra("przegrane", 123)
            var resultE = data?.getIntExtra("wynik", 123)
            won = wonE
            lost = lostE
            textView2.text = "Wygrane:" + wonE
            textView3.text = "Przegrane:" + lostE
            if(resultE == 1) {
                Toast.makeText(this, "Wygrales", Toast.LENGTH_LONG).show()
            }
            else if(resultE == 2) {
                Toast.makeText(this, "Przegrales", Toast.LENGTH_LONG).show()

            }
        }

    }

    fun onClick1(view: View) {
        val myIntent = Intent(this, Main2Activity::class.java)
        myIntent.putExtra("wygrane", won)
        myIntent.putExtra("przegrane", lost)
        startActivityForResult(myIntent, 123)
    }
}
