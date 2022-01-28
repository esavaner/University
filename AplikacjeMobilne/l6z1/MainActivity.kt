package com.example.l6z1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit : Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder()
            .baseUrl("https://newton.now.sh")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        button3.setOnClickListener {
            val call = retrofit.create(NewtonAPIZ::class.java).getResultZ("zeroes", editText.text.toString())
            call.enqueue(object : Callback<NewtonDTOZ> {
                override fun onFailure(call: Call<NewtonDTOZ>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<NewtonDTOZ>, response: Response<NewtonDTOZ>) {
                    var res = "["
                    for (i in response.body()?.result.orEmpty()) {
                        res += "$i, "
                    }
                    if (res.length > 1) {
                        res = res.dropLast(2)
                    }
                    res += "]"
                    textView.setText(res)
                }
            })
        }
    }

    fun onClick(view: View) {
        val btn = findViewById<Button>(view.id)
        val call = retrofit.create(NewtonAPI::class.java).getResult(btn.tag.toString(), editText.text.toString())
        call.enqueue(object : Callback<NewtonDTO> {
            override fun onFailure(call: Call<NewtonDTO>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewtonDTO>, response: Response<NewtonDTO>) {
                val body = response.body()
                textView.setText(body!!.result)
            }
        })
    }

    interface NewtonAPIZ {
        @GET("/{operation}/{expression}")
        fun getResultZ(
            @Path("operation") operation : String,
            @Path("expression") expression : String
        ) : Call<NewtonDTOZ>
    }

    interface NewtonAPI {
        @GET("/{operation}/{expression}")
        fun getResult(
            @Path("operation") operation: String,
            @Path("expression") expression: String
        ) : Call<NewtonDTO>
    }

    data class NewtonDTO (
        var operation : String,
        var expression : String,
        var result : String
    )

    data class NewtonDTOZ (
        var operation : String,
        var expression : String,
        var result : List<String>
    )
}
