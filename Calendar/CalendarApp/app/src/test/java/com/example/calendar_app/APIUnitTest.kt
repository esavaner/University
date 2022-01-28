package com.example.calendar_app

import com.example.calendar_app.data.CredentialsDTO
import com.example.calendar_app.network.RetrofitClient
import org.junit.Assert.*
import org.junit.Test


class APIUnitTest {
    @Test
    fun valid_authentication_works() {
        val api = RetrofitClient.getAPI()
        val c = CredentialsDTO("admin", "longpassword")
        val call = api.login(c)
        val response=call.execute()
        val header=response.headers().get("Authorization")
        val header2=response.headers().get("userid")
        assertNotNull(header)
        assertNotNull(header2)
        assert(header!!.contains("Bearer"))
        assert(header2!!.contains("1"))
    }
    @Test
    fun invalid_authentication_not_working() {
        val api = RetrofitClient.getAPI()
        val c = CredentialsDTO("admin", "long")
        val call = api.login(c)
        val response = call.execute()
        assert(response.code()==401)//unauthorized
    }
    //todo data save test
    //todo data load test
}