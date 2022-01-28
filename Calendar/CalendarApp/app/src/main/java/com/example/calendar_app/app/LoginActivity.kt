package com.example.calendar_app.app

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calendar_app.R
import com.example.calendar_app.network.RetrofitClient
import com.example.calendar_app.data.CredentialsDTO
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goBack(view: View) {
        finish()
    }

    fun login(view: View) {
        if (username.text.toString() == "") {
            username.hint = "Brak nazwy użytkownika!"
            return
        }

        if (password.text.toString() == "") {
            password.hint = "Brak hasła!"
            return
        }

        val call = RetrofitClient.getAPI().login(
            CredentialsDTO(
                username.text.toString(),
                password.text.toString()
            )
        )
        // try to log in
        status.text = "Logowanie..."
        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("am_calendar_login", "Could not connect with server.")
                status.text = "Nie można połączyć się z serwerem"
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    status.text = "Zalogowany!"
                    val sharedPref = getSharedPreferences(
                        "am_calendar_login_data", Context.MODE_PRIVATE
                    )
                    with(sharedPref.edit()) {
                        putBoolean("loggedIn", true)
                        putString("token", response.headers()["authorization"])
                        putLong("userID", response.headers()["userid"]!!.toLong())
                        commit()
                    }
                    finish()
                } else if (response.code() == 401) {
                    status.text = "Niepoprawna nazwa użytownika lub hasło"
                } else {
                    status.text = "Coś poszło nie tak"
                }
            }
        })
    }
}
