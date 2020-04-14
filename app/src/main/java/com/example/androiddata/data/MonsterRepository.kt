package com.example.androiddata.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.LOG_TAG
import com.example.androiddata.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


/**
 * Retrieves data from an assets file, parses the JSON into a list of model objects, and
 * publishes the data to the rest of the application through a LiveData object
 */

//Use an application reference not an Activity context
class MonsterRepository (val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()

    private val listType = Types.newParameterizedType(
        List::class.java,
        Monster::class.java
    )

    //Call the function when the class is instantiated
    init {
        getMonsterData()
        networkAvailable()
    }

    fun getMonsterData() {
        //Receiving and acting on a context, then releasing it does not cause a resource leak
        val text = FileHelper.getTextFromAssets(app, "monster_data.json")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter: JsonAdapter<List<Monster>> =
            moshi.adapter(listType)
        monsterData.value =
            adapter.fromJson(text) ?: emptyList()    //Use ?: operator for 'else"
    }

    //Check network status
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}