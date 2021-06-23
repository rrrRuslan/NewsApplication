package com.example.newsproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Source(
    @PrimaryKey val id: Any,
    @ColumnInfo val name: String
)