package com.example.newsproject.mainfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsproject.R
import com.example.newsproject.model.Article
import com.example.newsproject.retrofit.Controller
import com.example.newsproject.retrofit.RetrofitServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NewsListFragment : Fragment() {

    private var retrofitServices: RetrofitServices = Controller.retrofitService
    private var articleList: ArrayList<Article> = ArrayList()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresher: SwipeRefreshLayout

    private val apiKey = "031eaeb78954426a9a60188fd5b69177"
    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.news_list_fragment, container, false)

        activity?.title = "Новини"
        getHeadlinesByCountry()//initial news
        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)

        swipeRefresher = root.findViewById(R.id.swipe_refresh)
        swipeRefresher.setOnRefreshListener {
            getHeadlinesByCountry()
        }

        articleAdapter = ArticleAdapter(articleList)
        recyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = articleAdapter

        return root
    }

    @SuppressLint("CheckResult")
    private fun getHeadlinesByCountry() {
        retrofitServices.getTopHeadlinesByCountry("ua", apiKey).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Observable.fromIterable(it.articles) }
            .subscribeWith(createArticleObserver())
    }

    private fun createArticleObserver(): DisposableObserver<Article> {
        return object : DisposableObserver<Article>() {
            override fun onNext(article: Article) {
                if (!articleList.contains(article)) {
                    articleList.add(article)
                }
            }
            override fun onComplete() {
                Log.i("Successful call", "onComplete: completed call")
                articleAdapter.notifyDataSetChanged()
                swipeRefresher.isRefreshing = false
            }
            override fun onError(e: Throwable) {
                Log.e("createArticleObserver", "Article error: ${e.message}")
            }
        }
    }

}