package com.raywenderlich.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

// You are a RecycleView.Adapter which will hold
// Views of Type "ListSelectionViewHolder"
class ListSelectionRecyclerViewAdapter:
    RecyclerView.Adapter<ListSelectionViewHolder>()
{


    // Mock data to show our recycler view is working.
    var listTitles = arrayOf("Gel", "Chester", "Demby", "Paolo", "Shellane")

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
        return listTitles.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        // Weird thing is, I don't see a for loop here. So RecycleView is a more
        // declarative sorts of programming where you just configure it, and it loops itself
        // like a data-binding javascript framework?
        if (holder != null) {
            holder.listPosition.text = (position + 100).toString()
            holder.listTitle.text = listTitles[position]
        }
    }
    // This is where's im excited about.


}