package com.example.calendar_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


object RetrofitClient {
    private val BASE_API_URL = "https://calendar.d0ku.me" //TODO only for development purposes
    private var api: ServerAPI? = null
    fun getAPI(): ServerAPI {
        if (api == null) {
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create()

            api = Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ServerAPI::class.java)
        }
        return api!!
    }
}