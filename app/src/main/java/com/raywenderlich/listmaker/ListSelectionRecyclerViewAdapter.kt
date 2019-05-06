package com.raywenderlich.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

// You are a RecycleView.Adapter which will hold
// Views of Type "ListSelectionViewHolder"

// This will now accept a dynamic file from our MainActivity.kt
class ListSelectionRecyclerViewAdapter(
    val lists: ArrayList<TaskList>,
    val clickListener: ListSelectionRecyclerViewClickListener
):
    RecyclerView.Adapter<ListSelectionViewHolder>()
{


    // Mock data to show our recycler view is working.
    var listTitles = arrayOf("Gel", "Chester", "Demby", "Paolo", "Shellane")

    // Need this to bind our entries onClick to show the tasks they contain
    interface ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: TaskList)
    }

    // This lifecycle is OF TYPE ListSelectionViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        // Initialize the view with LayoutInflater in the first lifecycle.
        // This creates a layout dynamically
        // This is where your layout IS BOUND to your class, this coordinates them together. VERY
        // IMPORTANT.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_selection_view_holder, parent, false)

        // My View holder class is returned with a view variable that will inflate itself
        // and that uses the layout we created
        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        // I want my RecyclerView to have items as many as the mock data
        return lists.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        // Weird thing is, I don't see a for loop here. So RecycleView is a more
        // declarative sorts of programming where you just configure it, and it loops itself
        // like a data-binding javascript framework?
        if (holder != null) {
            // This is old code that still relied on the static data
            // holder.listPosition.text = (position + 100).toString()
            // holder.listTitle.text = listTitles[position]

            // This is the new code that now relies on the passed data
            holder.listPosition.text = (position + 100).toString()
            // oooh so now it uses the ".get" method for this lists object since
            // we are no longer using an array.
            holder.listTitle.text = lists.get(position).name

            // This is how to add an on click listener
            holder.itemView.setOnClickListener() {
                clickListener.listItemClicked(lists.get(position))
            }
        }
    }
    // This is where's im excited about.

    fun addList(list: TaskList) {
        lists.add(list)

        // whohohohoh every time you make changes to your object
        // call this "built-in" method for your RecyclerViewAdapter class
        // to inform it that data has been added and it should re render
        notifyDataSetChanged()
    }

}