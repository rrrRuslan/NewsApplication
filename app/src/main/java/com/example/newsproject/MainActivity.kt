package com.example.newsproject

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.newsproject.model.Source
import com.example.newsproject.room.NewsEntity
import com.example.newsproject.room.SavedNewsDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: SavedNewsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        db = Room.databaseBuilder(
            applicationContext,
            SavedNewsDB::class.java, "saved.db"
        ).build()

        val userDao = db.itemDao()
        GlobalScope.launch {
            userDao.insertAll(NewsEntity("test"))

            val users: List<NewsEntity> = userDao.getAll()
            Log.i("FROM DB", users.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "Settings item clicked", Toast.LENGTH_LONG)
                    .show()
                return true
            }

            R.id.save_news_menu -> {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,SavedFragment()).addToBackStack("").commit()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}