package com.austin.rooms2godemoapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

public interface EmailListService {

    @GET("/messages/users/{email}")
    suspend fun getEmail(@Path("email") email: String): Response<List<Email>>
}