package com.raywenderlich.listmaker

import android.os.Parcel
import android.os.Parcelable

// This class will be used to insert the entered value from
// the dialogue shown in FAB.
// Putting "Parcelable" so we can pass task list through intents
class TaskList constructor (val name: String, val tasks: ArrayList<String>
    = ArrayList<String>()) : Parcelable {

    // We will be using "SharedPreferences", to save a
    // key:value pair. I assume this will hold whatever our input
    // is on our create Dialogue
    constructor(source: Parcel) : this(
        // Boilerplate constructor to pass TaskList to Parcelable.
        source.readString(),
        source.createStringArrayList()
    ) {

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        // Self-explanatory.
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    override fun describeContents(): Int {
        return 0
    }

    // WE use a companion object instead of static when using Kotlin
    companion object CREATOR : Parcelable.Creator<TaskList> {
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source)

        override fun newArray(size: Int): Array<TaskList?> =
            arrayOfNulls(size)
    }

}