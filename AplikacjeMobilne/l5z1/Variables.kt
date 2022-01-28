package com.example.l5z1

import android.content.SharedPreferences

var pointsT = 0
var pointsB = 0
lateinit var sharedPref : SharedPreferences
var PREFS_NAME = "pong"
var won = 0
var lost = 0

var gameType = 1
var botSpeed = 13f

var ballX = 500f
var ballY = 500f
var dx = 0f
var dy = 15f
const val size = 35f

const val rectSize = 270f

var bottomRectX = 400f
var bottomRectY = 1660f

var topRectX = 400f
var topRectY = 100f

