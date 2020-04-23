package com.example.androiddata.utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.androiddata.ITEM_TYPE_KEY

class PrefsHelper {

    companion object{
        private fun preferences (context: Context) : SharedPreferences =
            context.getSharedPreferences("default", 0)

        fun setItemType (context: Context, type: String) {
            //Get instantiate the preferences object/file, in edit mode add
            //the key value pair
            preferences(context)
                .edit()
                .putString(ITEM_TYPE_KEY, type)
                .apply()
        }

        fun getItemType (context: Context): String {
            return preferences(context).getString(ITEM_TYPE_KEY, "list")!!
        }
    }
}