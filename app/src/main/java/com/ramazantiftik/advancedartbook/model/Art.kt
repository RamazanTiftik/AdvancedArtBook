package com.ramazantiftik.advancedartbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    val artName: String,
    val artistName: String,
    val year: Int,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null
)