package com.example.androiddata.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androiddata.IMAGE_BASE_URL
import com.squareup.moshi.Json

/**
 * Data entity class that defines the SQLite database via Room
 */

@Entity(tableName = "monster")
data class Monster (
    @PrimaryKey(autoGenerate = true)
    val monsterId: Int,
    val imageFile: String,
    val monsterName: String,
    val caption: String,
    val description: String,
    val price: Double,
    val scariness: Int)

{
    //Read-only functions where getter calculates the location on the website
    val imageUrl
        get() = "$IMAGE_BASE_URL/$imageFile.webp"

    val thumbnailUrl
        get() = "$IMAGE_BASE_URL/${imageFile}_tn.webp"
}