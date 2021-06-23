package com.example.newsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.newsproject.mainfragment.ArticleAdapter
import com.example.newsproject.model.Article
import com.example.newsproject.room.SavedNewsDB
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {

    private var articleList: ArrayList<Article> = ArrayList()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: SavedNewsDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.saved_fragment, container, false)

        articleAdapter = ArticleAdapter(articleList)
        recyclerView = root.findViewById(R.id.saved_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = articleAdapter
        activity?.title = "Збережені"

        db = Room.databaseBuilder(
            activity?.applicationContext!!,
            SavedNewsDB::class.java, "saved.db"
        ).build()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            val savedList = db.itemDao().getAll()
            savedList.forEach {
                if (it.article.length>20 && !articleList.contains(Gson().fromJson(it.article,Article::class.java))){
                    articleList.add(Gson().fromJson(it.article,Article::class.java))
                }
            }
            activity?.runOnUiThread {
                articleAdapter.notifyDataSetChanged()
            }
        }
    }


}