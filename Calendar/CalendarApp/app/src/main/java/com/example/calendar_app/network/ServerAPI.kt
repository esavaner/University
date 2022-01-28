package com.example.calendar_app.network

import com.example.calendar_app.data.Countdown
import com.example.calendar_app.data.CredentialsDTO
import com.example.calendar_app.data.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET

interface ServerAPI {
    @POST("/user/login")
    fun login(@Body c : CredentialsDTO) : Call<Void>
    @POST("/sec/event/save")
    fun saveEvents(@Header("authorization") token:String, @Body l: List<Event>) : Call<Void>
    @POST("/sec/countdown/save")
    fun saveCountdowns(@Header("authorization") token:String, @Body l: List<Countdown>) : Call<Void>
    @GET("/sec/event/all")
    fun getEvents(@Header("authorization") token:String) : Call<List<Event>>
    @GET("/sec/countdown/all")
    fun getCountdowns(@Header("authorization") token:String) : Call<List<Countdown>>
}