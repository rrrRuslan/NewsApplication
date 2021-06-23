package com.example.newsproject.room

import androidx.room.*

@Dao
interface SavedNewsDao {

    @Query("SELECT * FROM saved_news_table")
    fun getAll(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg items: NewsEntity)

    @Delete
    fun delete(item: NewsEntity)

    @Query("DELETE FROM saved_news_table")
    fun deleteAll()

}