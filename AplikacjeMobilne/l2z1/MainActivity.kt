package com.example.l2z1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pointsCr = 0
    private var pointsCi = 0
    private var buttonTable = ArrayList<View>()
    private var playerID = 0 //circle
    private val board = Array(5, {IntArray(5)})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageCross.visibility = View.INVISIBLE
        buttonTable.add(imageButton00)
        buttonTable.add(imageButton01)
        buttonTable.add(imageButton02)
        buttonTable.add(imageButton03)
        buttonTable.add(imageButton04)
        buttonTable.add(imageButton10)
        buttonTable.add(imageButton11)
        buttonTable.add(imageButton12)
        buttonTable.add(imageButton13)
        buttonTable.add(imageButton14)
        buttonTable.add(imageButton20)
        buttonTable.add(imageButton21)
        buttonTable.add(imageButton22)
        buttonTable.add(imageButton23)
        buttonTable.add(imageButton24)
        buttonTable.add(imageButton30)
        buttonTable.add(imageButton31)
        buttonTable.add(imageButton32)
        buttonTable.add(imageButton33)
        buttonTable.add(imageButton34)
        buttonTable.add(imageButton40)
        buttonTable.add(imageButton41)
        buttonTable.add(imageButton42)
        buttonTable.add(imageButton43)
        buttonTable.add(imageButton44)
        reset()

    }

    fun reset() {
        for(i in 0..4) {
            for(j in 0..4) {
                board[i][j] = -1
            }
        }
        for(i in 0 until buttonTable.size) {
            buttonTable[i].setBackgroundResource(R.drawable.fieldempty2)
        }
        playerID = 0
        imageCross.visibility = View.INVISIBLE
        imageCircle.visibility = View.VISIBLE
    }

    fun click(view: View) {
        when(view) {
            imageButton00 -> check(view, 0,0)
            imageButton01 -> check(view, 0,1)
            imageButton02 -> check(view, 0,2)
            imageButton03 -> check(view, 0,3)
            imageButton04 -> check(view, 0,4)
            imageButton10 -> check(view, 1,0)
            imageButton11 -> check(view, 1,1)
            imageButton12 -> check(view, 1,2)
            imageButton13 -> check(view, 1,3)
            imageButton14 -> check(view, 1,4)
            imageButton20 -> check(view, 2,0)
            imageButton21 -> check(view, 2,1)
            imageButton22 -> check(view, 2,2)
            imageButton23 -> check(view, 2,3)
            imageButton24 -> check(view, 2,4)
            imageButton30 -> check(view, 3,0)
            imageButton31 -> check(view, 3,1)
            imageButton32 -> check(view, 3,2)
            imageButton33 -> check(view, 3,3)
            imageButton34 -> check(view, 3,4)
            imageButton40 -> check(view, 4,0)
            imageButton41 -> check(view, 4,1)
            imageButton42 -> check(view, 4,2)
            imageButton43 -> check(view, 4,3)
            imageButton44 -> check(view, 4,4)
        }
    }

    fun nextPlayer() {
        if(playerID == 0) {
            imageCross.visibility = View.VISIBLE
            imageCircle.visibility = View.INVISIBLE
            playerID = (playerID + 1).rem(2)
        }
        else {
            imageCross.visibility = View.INVISIBLE
            imageCircle.visibility = View.VISIBLE
            playerID = (playerID + 1).rem(2)
        }
    }

    fun check(view: View, y: Int, x: Int) {
        if(board[x][y] == -1) {
            if(playerID == 0) {
                view.setBackgroundResource(R.drawable.fieldcircle2)
                board[x][y] = 0
            }
            else {
                view.setBackgroundResource(R.drawable.fieldcross2)
                board[x][y] = 1
            }
            if(checkWin(x, y)) {
                Toast.makeText(this, "Wygral gracz $playerID" , Toast.LENGTH_LONG).show()
                if(playerID == 0) {
                    pointsCi++
                    pointsCircle.text = "Wygrane: $pointsCi"
                }
                else {
                    pointsCr++
                    pointsCross.text = "Wygrane: $pointsCr"
                }
                reset()
            }
            else if(checkDraw()) {
                reset()
                Toast.makeText(this, "Remis", Toast.LENGTH_LONG).show()
            }
            else
                nextPlayer()
        }
    }

    fun checkWin(x: Int, y: Int): Boolean {
        return (board[x][0] == playerID
                && board[x][1] == playerID
                && board[x][2] == playerID
                && board[x][3] == playerID
                && board[x][4] == playerID
                ||
                board[0][y] == playerID
                && board[1][y] == playerID
                && board[2][y] == playerID
                && board[3][y] == playerID
                && board[4][y] == playerID
                ||
                board[0][0] == playerID
                && board[1][1] == playerID
                && board[2][2] == playerID
                && board[3][3] == playerID
                && board[4][4] == playerID
                ||
                board[0][4] == playerID
                && board[1][3] == playerID
                && board[2][2] == playerID
                && board[3][1] == playerID
                && board[4][0] == playerID)
    }
    fun checkDraw(): Boolean {
        for(i in 0..4) {
            for(j in 0..4) {
                if(board[i][j] == -1) {
                    return false
                }
            }
        }
        return true
    }
}
