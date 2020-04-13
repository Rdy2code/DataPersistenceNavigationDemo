package com.example.androiddata.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.LOG_TAG
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import com.example.androiddata.data.MonsterRepository
import com.example.androiddata.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

public class MainViewModel(app: Application) : AndroidViewModel(app) {

    //When ViewModel is initialized from the UI, it calls into the repository which does all of
    // the data acquisition work

    //Instantiate the Repository
    private val dataRepo = MonsterRepository(app)
    //Get reference to LiveData object
    val monsterData = dataRepo.monsterData
}
