package com.example.androiddata.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.androiddata.data.MonsterRepository

public class MainViewModel(app: Application) : AndroidViewModel(app) {

    //When ViewModel is initialized from the UI, it calls into the repository which does all of
    // the data acquisition work

    //Instantiate the Repository
    private val dataRepo = MonsterRepository(app)
    //Get reference to LiveData object
    val monsterData = dataRepo.monsterData

    fun refreshLayout() {
        dataRepo.refreshData()
    }
}
