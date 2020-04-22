package com.example.androiddata.utilities

import android.app.Application
import android.content.Context
import java.io.File

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

        //Write to files in internal storage
        fun saveTextToFile(app: Application, json: String?) {
            //Use cacheDir instead of filesDir to save in cache, which will be cleaned up by the OS
            //if the system runs low on memory
            val file = File(app.getExternalFilesDir("monsters"), "monsters.json")
            file.writeText(json?: "", Charsets.UTF_8)
        }

        //Read from files in internal storage
        fun readFromTextFile (app: Application): String? {
            val file = File(app.getExternalFilesDir("monsters"), "monsters.json")
            return if (file.exists()) {
                file.readText()
            } else {
                null
            }
        }
    }
}