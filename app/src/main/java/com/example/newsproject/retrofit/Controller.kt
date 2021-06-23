package com.example.newsproject.retrofit

object Controller {

    private val BASE_URL = "https://newsapi.org/v2/"

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}