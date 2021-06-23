package com.example.newsproject.mainfragment

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsproject.model.Article
import com.example.newsproject.model.NewsResponse
import com.example.newsproject.model.Source
import com.example.newsproject.retrofit.Controller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel: ViewModel() {

    val retrofitServices = Controller.retrofitService
    private val apiKey = "031eaeb78954426a9a60188fd5b69177"

    var articles = MutableLiveData(mutableListOf(Article("1", "","","", Source(1,""),"","","")))

//    fun initArticles(){
//        retrofitServices.getTopHeadlinesByCountryCall("ua", apiKey ).enqueue(object :
//            Callback<NewsResponse> {
//
//            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
//                val response = response.body() as NewsResponse
//                articles.value?.addAll(response.articles)
//            }
//        } )
//    }
}