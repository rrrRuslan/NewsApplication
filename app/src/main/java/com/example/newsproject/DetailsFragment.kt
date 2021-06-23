package com.example.newsproject

import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.newsproject.model.Article
import com.example.newsproject.room.NewsEntity
import com.example.newsproject.room.SavedNewsDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private lateinit var image:ImageView
    private lateinit var title:TextView
    private lateinit var description:TextView
    private lateinit var date:TextView
    private lateinit var url:TextView
    private lateinit var fab:FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = Gson().fromJson(arguments?.getString("article"), Article::class.java)
        activity?.title = "Детальніше"
        bindViews(view)
        Picasso.get()
            .load(article.urlToImage)
            .centerCrop()
            .fit()
            .into(image)

        val db = Room.databaseBuilder(
            activity?.applicationContext!!,
            SavedNewsDB::class.java, "saved.db"
        ).build()

        date.text = article.publishedAt.replace('T', ' ').dropLast(4)// trim timezone
        title.text = article.title
        description.text = article.description
        url.text = "Джерело: ${article.url}"
        fab.setOnClickListener {
            GlobalScope.launch {
                db.itemDao().insertAll(NewsEntity(Gson().toJson(article)))
                requireActivity().runOnUiThread{
                    fab.setImageResource(R.drawable.ic_baseline_star_24)
                }
            }

        }

    }



    private fun bindViews(view: View){
        image = view.findViewById(R.id.secondFr_image)
        title = view.findViewById(R.id.secondFr_article_descr)
        description = view.findViewById(R.id.secondFr_article_content)
        date = view.findViewById(R.id.secondFr_article_date)
        url = view.findViewById(R.id.secondFr_article_url)
        fab = view.findViewById(R.id.save_fab)

    }

//    private fun parseUrl(string: String):String{
//        return Html.fromHtml(string)
//    }
}