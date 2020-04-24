package com.example.androiddata.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.androiddata.data.Monster
import com.example.androiddata.data.MonsterRepository

//using val keyword with 'app' in constructor makes it available for the lifetime of the app
public class SharedViewModel(val app: Application) : AndroidViewModel(app) {

    //When ViewModel is initialized from the UI, it calls into the repository which does all of
    // the data acquisition work

    //Instantiate the Repository
    private val dataRepo = MonsterRepository(app)

    //Get reference to LiveData object. This is the data we need for the detail fragment UI
    val monsterData = dataRepo.monsterData

    //Create LiveData object that contains instance of Monster class as shareable between fragments
    val selectedMonster = MutableLiveData<Monster>()

    //LiveData object for SettingsActivity
    val activityTitle = MutableLiveData<String>()

    init {
        updateActivityTitle()
    }

    fun refreshLayout() {
        dataRepo.refreshDataFromWebService()
    }

    //We want to call this function when the viewmodel is created, so call it from within an
    //init block, above
    fun updateActivityTitle() {
        val signature =
            PreferenceManager.getDefaultSharedPreferences(app)
                .getString("signature", "Monster fan")

        //Setting the value of the mutable live data object activityTitle to 'value' broadcasts to
        //all objects that are listening. Add an observer to the mainfragment class
        activityTitle.value = "Stickers for $signature"

    }
}
