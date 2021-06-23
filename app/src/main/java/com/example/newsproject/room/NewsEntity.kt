package com.example.newsproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsproject.model.Source

@Entity(tableName = "saved_news_table")
data class NewsEntity(
    @ColumnInfo(name = "article") val article: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
