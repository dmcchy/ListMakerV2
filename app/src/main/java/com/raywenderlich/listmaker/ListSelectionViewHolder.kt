package com.raywenderlich.listmaker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

// Class will accept a view.
class ListSelectionViewHolder (itemView: View):
    RecyclerView.ViewHolder(itemView)
{
    // Hmmmm

    val listPosition = itemView?.findViewById<TextView>(R.id.itemNumber)
        as TextView

    val listTitle = itemView?.findViewById<TextView>(R.id.itemString)
        as TextView
}