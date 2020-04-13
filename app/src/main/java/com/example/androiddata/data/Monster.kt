package com.example.androiddata.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Class properties must match the fields in the json file

//@JsonClass(generateAdapter = true)
data class Monster (
    //Use @Json annotation to define name in the json file. This lets you use any value name you want
    @Json(name = "imageFile") val imageFile: String,
    @Json(name = "monsterName") val monsterName: String,
    @Json(name = "caption") val caption: String,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Double,
    @Json(name = "scariness") val scariness: Int
)