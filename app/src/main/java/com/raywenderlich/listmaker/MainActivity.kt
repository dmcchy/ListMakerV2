package com.raywenderlich.listmaker

import android.content.Intent
import android.net.Uri
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
import android.widget.FrameLayout

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        ListSelectionFragment.OnFragmentInteractionListener
{

    private var largeScreen = false
    private var listFragment: ListDetailFragment? = null

    private var fragmentContainer: FrameLayout? = null

    private var listSelectionFragment: ListSelectionFragment =
        ListSelectionFragment.newInstance()

    // Activity extras to be passed to other activities.
    companion object {
        val INTENT_LIST_KEY = "list"
        val LIST_DETAIL_REQUEST_CODE = 123
    }

    /**
     * Variables below moved to context
     */

    // This is to read and write the contents of my sharedPreferences file
    // val listDataManager: ListDataManager = ListDataManager(this)

    // Create a RecyclerView some time in the "future".
    // lateinit var listsRecyclerView: RecyclerView

    /**
     * end of variables below moved to context
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        listSelectionFragment = supportFragmentManager.findFragmentById(R.id.list_selection_fragment)
            as ListSelectionFragment

        fragmentContainer = findViewById(R.id.fragment_container)

        largeScreen = fragmentContainer != null

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, listSelectionFragment)
            .commit()

        fab.setOnClickListener { view ->
            // Ah, here do we hook the (FAB) for listening behaviours.
            showCreateListDialog()

            // This was for example purposes that was generated as a placeholder when selecting
            // the initial theme.
            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        /*
        val lists = listDataManager.readLists()

        // Uncomment later, after the ViewAdapter class "ListSelectionRecyclerViewAdapter" is created
        listsRecyclerView = findViewById(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Ooooh now we pass it here. lol
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        */
    }

    /**
     * This code listens to any activity this code has started that is wanting to pass back data.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE) {

            /**
             * If I get results from ListDetailActivity, I save it into my
             * listDataManager object.
             *
             * So this is the way to duplicating listDataManager in storing our data.
             */

            data?.let {
                // Fragment will now handle this budddy, sorry.
                // listDataManager.saveList(data.getParcelableExtra(INTENT_LIST_KEY))

                listSelectionFragment.saveList(data.getParcelableExtra(INTENT_LIST_KEY))
            }
        }
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
            listSelectionFragment.addList(list)


            dialog.dismiss()
            // After creating a new list, pass it to the new activity.
            showListDetail(list)
        }

        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {

        if (!largeScreen) {
            // Pass the intent to next Activity
            val listDetailIntent = Intent(this, ListDetailActivity::class.java)

            // Adding an extra info to tell the new activity, "hey I want to display a list"
            listDetailIntent.putExtra(INTENT_LIST_KEY, list)

            // Execute the move to another new activity,
            // but now we have "ForResult" which means that this activity opening the new activity
            // EXPECTS to hear something back from it.
            startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
        } else {
            // Summon the fragment and call it.
//            title = list.name
//
//            listFragment = ListDetailFragment.newInstance(list)
//
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, listFragment,
//                    getString(R.string.list_fragment_tag))
//                .addToBackStack(null)
//                .commit()
//
//            fab.setOnClickListener { view ->
//                showCreateTaskDialog()
//            }
        }
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                val task = taskEditText.text.toString()
                listFragment?.addTask(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        title = resources.getString(R.string.app_name)

        listFragment?.list?.let {
            listSelectionFragment?.listDataManager?.saveList(it)
        }

        if (listFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .remove(listFragment!!)
                .commit()

            listFragment = null
        }

        fab.setOnClickListener { view ->
            showCreateListDialog()
        }
    }

    override fun onListItemClicked(list: TaskList) {
        showListDetail(list)
    }
}
