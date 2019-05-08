package com.raywenderlich.listmaker

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListSelectionFragment : Fragment(),
        ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener
{

    // Hmmmm... These will be our data.
    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView

    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null

    companion object {

        fun newInstance(): ListSelectionFragment {
            val fragment = ListSelectionFragment()

            return fragment
        }
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

    fun addList(list: TaskList) {
        listDataManager.saveList(list)

        val recyclerAdapter = listsRecyclerView.adapter as
                ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list)
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context

            // Here config listDataManager
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    // New lifecycle used in book.
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val lists = listDataManager.readLists()
        view?.let {
            listsRecyclerView = it.findViewById<RecyclerView>(R.id.lists_recyclerview)
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists,
                this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }


}
