package com.example.androiddata.data

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.LOG_TAG
import com.example.androiddata.WEB_SERVICE_URL
import com.example.androiddata.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Retrieves data from an assets file, parses the JSON into a list of model objects, and
 * publishes the data to the rest of the application through a LiveData object
 */

//Use an application reference not an Activity context
class MonsterRepository (val app: Application) {

    val monsterData = MutableLiveData<List<Monster>>()

    //Call the static method in the database with app as context then get an instance of the
    //Dao
    private val monsterDao = MonsterDatabase.getDatabase(app).monsterDao()

    //Call the function when the class is instantiated
    //Create a coroutine so database access can be done in a background thread
    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data = monsterDao.getAll()
            if (data.isEmpty()) {
                callWebService()
            } else {
                monsterData.postValue(data)
                //Switch to the main thread to notify user that database is being used
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Using local data", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //Check network status
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    //Calling a function with suspend keyword means it must be called inside a coroutine
    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using web service", Toast.LENGTH_LONG).show()}
            Log.i(LOG_TAG, "Calling web service")
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(MonsterWebService::class.java)
            val serviceData = service.getMonsterData().body() ?: emptyList()
            monsterData.postValue(serviceData)
            //Clear the table and repopulate with most current data from web service
            monsterDao.deleteAll()
            monsterDao.insertMonsters(serviceData)
        }
    }

    fun refreshDataFromWebService() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }
}