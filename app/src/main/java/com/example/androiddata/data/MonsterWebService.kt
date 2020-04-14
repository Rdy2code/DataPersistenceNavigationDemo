package com.example.androiddata.data

import retrofit2.Response
import retrofit2.http.GET

/**
 * Web service interface for retrofit web call. Function is designed to be called as part of a
 * coroutine
 */

interface MonsterWebService {

    @GET("/feed/monster_data.json")
    suspend fun getMonsterData(): Response<List<Monster>>
}