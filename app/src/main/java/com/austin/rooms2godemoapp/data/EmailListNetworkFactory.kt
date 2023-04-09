package com.austin.rooms2godemoapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmailListNetworkFactory {

    val url = "https://vcp79yttk9.execute-api.us-east-1.amazonaws.com/"
    fun getInstance(): Retrofit {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}