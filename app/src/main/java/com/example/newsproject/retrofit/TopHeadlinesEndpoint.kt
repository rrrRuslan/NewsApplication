package com.example.newsproject.retrofit

import com.example.newsproject.model.NewsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @GET("top-headlines")
    fun getTopHeadlinesByCountry(
            @Query("country") country: String,
            @Query("apiKey") apiKey: String
    ): Observable<NewsResponse>

    @GET("top-headlines")
    fun getUserSearchInput(
            @Query("apiKey") apiKey: String,
            @Query("q") q: String
    ): Observable<NewsResponse>

}