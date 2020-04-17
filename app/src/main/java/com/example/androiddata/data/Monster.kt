package com.example.androiddata.data

import com.example.androiddata.IMAGE_BASE_URL
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Class properties must match the fields in the json file

//@JsonClass(generateAdapter = true)
data class Monster (
    //Use @Json annotation to define name in the json file. This lets you use any value name you want
    @field:Json(name="imageFile") val imageFile: String,
    @field:Json(name="monsterName") val monsterName: String,
    @field:Json(name="caption") val caption: String,
    @field:Json(name="description") val description: String,
    @field:Json(name="price") val price: Double,
    @field:Json(name="scariness") val scariness: Int
) {
    //Read-only functions
    val imageUrl
        get() = "$IMAGE_BASE_URL/$imageFile.webp"

    val thumbnailUrl
        get() = "$IMAGE_BASE_URL/${imageFile}_tn.webp"
}