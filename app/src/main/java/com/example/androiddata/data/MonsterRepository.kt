package com.example.androiddata.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.LOG_TAG
import com.example.androiddata.WEB_SERVICE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Retrieves data from an assets file, parses the JSON into a list of model objects, and
 * publishes the data to the rest of the application through a LiveData object
 */

//Use an application reference not an Activity context
class MonsterRepository (val app: Application) {
    val monsterData = MutableLiveData<List<Monster>>()

//    private val listType = Types.newParameterizedType(
//        List::class.java,
//        Monster::class.java
//    )

    //Call the function when the class is instantiated
    init {
        //getMonsterData()

        //Put this in a coroutine on a background thread
        refreshData()
    }

    //Use this function when parsing JSON explicitly with Moshi without retrofit

//    fun getMonsterData() {
//        //Receiving and acting on a context, then releasing it does not cause a resource leak
//        val text = FileHelper.getTextFromAssets(app, "monster_data.json")
//        val moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        val adapter: JsonAdapter<List<Monster>> =
//            moshi.adapter(listType)
//        monsterData.value =
//            adapter.fromJson(text) ?: emptyList()    //Use ?: operator for 'else"
//    }

    //Check network status
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling web service")
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(MonsterWebService::class.java)
            val serviceData = service.getMonsterData().body() ?: emptyList()
            monsterData.postValue(serviceData)
        }
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }
}