package com.raywenderlich.listmaker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener
{

    // Activity extras to be passed to other activities.
    companion object {
        val INTENT_LIST_KEY = "list"
    }

    // This is to read and write the contents of my sharedPreferences file
    val listDataManager: ListDataManager = ListDataManager(this)

    // Create a RecyclerView some time in the "future".
    lateinit var listsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // Ah, here do we hook the (FAB) for listening behaviours.
            showCreateListDialog()

            // This was for example purposes that was generated as a placeholder when selecting
            // the initial theme.
            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        val lists = listDataManager.readLists()

        // Uncomment later, after the ViewAdapter class "ListSelectionRecyclerViewAdapter" is created
        listsRecyclerView = findViewById(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Ooooh now we pass it here. lol
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Bind (FAB) to Dialogue
    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        // Add a "text" dialogue in this context
        val builder = AlertDialog.Builder(this)
        // This is imported from android
        var listTitleEditText = EditText(this)
        // This is a config
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT // Can also be typed, "number"

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) {dialog, i ->
            // Ok I think here's where we store the newly added elements captured in dialog.
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)

            // WE are reusing my adapter created early on the display arrays.
            // We're now adding an addList function.
            val recyclerAdapter = listsRecyclerView.adapter as
                    ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)


            dialog.dismiss()
            // After creating a new list, pass it to the new activity.
            showListDetail(list)
        }

        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        // Pass the intent to next Activity
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)

        // Adding an extra info to tell the new activity, "hey I want to display a list"
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)

        // Execute the move to another new activity
        startActivity(listDetailIntent)
    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }
}
