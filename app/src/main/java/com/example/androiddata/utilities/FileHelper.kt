package com.example.androiddata.utilities

import android.content.Context

class FileHelper {

    companion object {
        //use call opens and closes the reader
        //Two different ways to read the json into memory, either from the resources folder
        //or from an assets folder
       fun getTextFromResources (context: Context, resourceId: Int): String {
           return context.resources.openRawResource(resourceId).use {
               it.bufferedReader().use {
                   it.readText()
               }
           }
       }

        fun getTextFromAssets (context: Context, fileName: String): String {
            return context.assets.open(fileName).use {
                it.bufferedReader().use {
                    it.readText()
                }
            }
        }
    }
}