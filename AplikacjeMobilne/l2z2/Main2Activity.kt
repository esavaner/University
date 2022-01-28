package com.example.l2z2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.collections.ArrayList

class Main2Activity : AppCompatActivity() {
    private var won = 0
    private var lost = 0
    private var txtList = ArrayList<String>()
    private var counter = 0
    private var splitArr = "start".toCharArray()
    private var qArr = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var wonE = intent.getIntExtra("wygrane", won)
        var lostE = intent.getIntExtra("przegrane", lost)
        won = wonE
        lost = lostE
        var reader = Scanner(resources.openRawResource(R.raw.slowa))
        while(reader.hasNextLine()){
            var line = reader.nextLine()
            txtList.add(line)
        }
        start()
    }
    fun updateTxt() {
        var text = ""
        for(i in 0 until qArr.size) {
            text+= qArr[i] + " "
        }
        textView.text = text
    }

    fun start() {
        imageView.setBackgroundResource(R.drawable.wisielec00sized)
        qArr.clear()
        var next = Random().nextInt(txtList.size)
        splitArr = txtList[next].toCharArray()
        textView.text = splitArr.size.toString()
        for(i in 0 until splitArr.size) {
            qArr.add("? ")
        }
        updateTxt()
    }

    fun onClick(view : View) {
        when(view) {
            buttonA -> check("a")
            buttonB -> check("b")
            buttonC -> check("c")
            buttonD -> check("d")
            buttonE -> check("e")
            buttonF -> check("f")
            buttonG -> check("g")
            buttonH -> check("h")
            buttonI -> check("i")
            buttonJ -> check("j")
            buttonK -> check("k")
            buttonL -> check("l")
            buttonM -> check("m")
            buttonN -> check("n")
            buttonO -> check("o")
            buttonP -> check("p")
            buttonQ -> check("q")
            buttonR -> check("r")
            buttonS -> check("s")
            buttonT -> check("t")
            buttonU -> check("u")
            buttonW -> check("w")
            buttonV -> check("v")
            buttonX -> check("x")
            buttonY -> check("y")
            buttonZ -> check("z")
        }
    }

    fun check(s: String) {
        var found = false
        var wonGame= true
        for(i in 0 until splitArr.size) {
            if(s.toCharArray()[0] == splitArr[i]) {
                qArr[i] = splitArr[i].toString()
                found = true
            }
            if(qArr[i] == "? " ) {
                wonGame = false
            }
        }
        updateTxt()
        if(!found) {
            when (counter) {
                0 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec01sized)
                    counter = (counter + 1).rem(7)
                }
                1 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec02sized)
                    counter = (counter + 1).rem(7)
                }
                2 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec03sized)
                    counter = (counter + 1).rem(7)
                }
                3 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec04sized)
                    counter = (counter + 1).rem(7)
                }
                4 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec05sized)
                    counter = (counter + 1).rem(7)
                }
                5 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec06sized)
                    counter = (counter + 1).rem(7)
                }
                6 -> {
                    imageView.setBackgroundResource(R.drawable.wisielec07sized)
                    counter = (counter + 1).rem(7)
                    val myIntent = Intent()
                    myIntent.putExtra("wygrane", won)
                    myIntent.putExtra("przegrane", lost + 1)
                    myIntent.putExtra("wynik", 2)
                    setResult(Activity.RESULT_OK, myIntent)
                    finish()
                }
            }
        }
        if(wonGame) {
            val myIntent = Intent()
            myIntent.putExtra("wygrane", won + 1)
            myIntent.putExtra("przegrane", lost)
            myIntent.putExtra("wynik", 1)
            setResult(Activity.RESULT_OK, myIntent)
            finish()
        }
    }
}
