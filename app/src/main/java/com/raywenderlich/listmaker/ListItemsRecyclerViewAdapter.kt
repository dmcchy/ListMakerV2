package com.raywenderlich.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListItemsRecyclerViewAdapter(val list: TaskList):
    RecyclerView.Adapter<ListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, holder: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.task_view_holder, parent, false)

        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.tasks.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        if (holder != null) {
            holder.taskTextView.text = list.tasks[position]
        }
    }

}