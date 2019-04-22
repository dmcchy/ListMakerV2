package com.raywenderlich.listmaker

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

class MainActivity : AppCompatActivity() {

    // Create a RecyclerView some time in the "future".
    lateinit var listsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Uncomment later, after the ViewAdapter class "ListSelectionRecyclerViewAdapter" is created
        listsRecyclerView = findViewById(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter()
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

        // Add a dialogue in this context
        val builder = AlertDialog.Builder(this)
        var listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) {dialog, i ->
            dialog.dismiss()
        }
    }
}
