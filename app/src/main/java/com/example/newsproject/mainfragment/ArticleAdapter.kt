package com.example.newsproject.mainfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.R
import com.example.newsproject.model.Article
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.time.milliseconds

class ArticleAdapter(private var articleList: ArrayList<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val placeholderImageUrl =
        "http://www.ll-mm.com/images/placeholders/image-placeholder.jpg"


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ArticleViewHolder {
        val itemView: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.article_item, viewGroup, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = articleList[position]
        setPropertiesForArticleViewHolder(holder, article)
        //trying to implement transitions
        holder.cardView.setOnClickListener {
//            val extras = FragmentNavigatorExtras(holder.urlToImage to "imageView")
            Navigation.findNavController(it)
                .navigate(R.id.action_NewsListFragment_to_DetailsFragment,
                    bundleOf("article" to Gson().toJson(article)),
                    navOptions {
                        anim {
                            enter = android.R.animator.fade_in
                            exit = android.R.animator.fade_out
                            popEnter = android.R.animator.fade_in
                            popExit = android.R.animator.fade_out
                        }
                    }
                )

        }
    }

    private fun setPropertiesForArticleViewHolder(
        articleViewHolder: ArticleViewHolder,
        article: Article
    ) {
        checkForUrlToImage(article, articleViewHolder)
        articleViewHolder.title.text = article.title
        articleViewHolder.description.text = article.description

    }

    private fun checkForUrlToImage(article: Article, articleViewHolder: ArticleViewHolder) {
        if (article.urlToImage == null || article.urlToImage.isEmpty()) {
            Picasso.get()
                .load(placeholderImageUrl)
                .centerCrop()
                .fit()
                .into(articleViewHolder.urlToImage)
        } else {
            Picasso.get()
                .load(article.urlToImage)
                .centerCrop()
                .fit()
                .into(articleViewHolder.urlToImage)
        }
    }

    override fun getItemCount(): Int = articleList.size

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cardView: CardView = view.findViewById(R.id.card_view)
        val urlToImage: ImageView = view.findViewById(R.id.article_urlToImage)
        val title: TextView = view.findViewById(R.id.article_title)
        val description: TextView = view.findViewById(R.id.article_description)
    }

}