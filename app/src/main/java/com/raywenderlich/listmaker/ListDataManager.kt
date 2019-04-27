package com.raywenderlich.listmaker

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log

// Hmmm, the TaskList object is the type we are going to be storing
// here?
class ListDataManager(val context: Context) {
    fun saveList(list: TaskList) {
        // Get reference
        // Instantiate PreferenceManager which is going to be used
        // to hold our list. (this is our store)
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
            .edit() // Called edit let's me write data to this store.

        // Save and persist method.
        // list.name is the "key" (very important), our "value"
        // are the tasks which is converted to a "HashSet"
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()
    }

    // A function that will return an array of "TaskList"(s)
    fun readLists(): ArrayList<TaskList> {
        // Get reference
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        val sharedPreferenceContents = sharedPreferences.all // hhohoho

        // This is merely a container to be populated with the entries below.
        // We open up the sharedPreferences object where we stored our old entries.
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferenceContents) {
            // Is this the decryption part?
            val itemsHashSet = taskList.value as HashSet<String>
            val list = TaskList(taskList.key, ArrayList(itemsHashSet))

            taskLists.add(list)
        }

        return taskLists
    }
}