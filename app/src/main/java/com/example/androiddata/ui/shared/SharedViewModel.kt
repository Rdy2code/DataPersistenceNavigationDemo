package com.example.androiddata.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androiddata.data.Monster
import com.example.androiddata.data.MonsterRepository

public class SharedViewModel(app: Application) : AndroidViewModel(app) {

    //When ViewModel is initialized from the UI, it calls into the repository which does all of
    // the data acquisition work

    //Instantiate the Repository
    private val dataRepo = MonsterRepository(app)

    //Get reference to LiveData object. This is the data we need for the detail fragment UI
    val monsterData = dataRepo.monsterData

    //Create LiveData object that contains instance of Monster class as shareable between fragments
    val selectedMonster = MutableLiveData<Monster>()

    fun refreshLayout() {
        dataRepo.refreshDataFromWebService()
    }
}
