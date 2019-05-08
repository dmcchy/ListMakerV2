package com.raywenderlich.listmaker

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.widget.EditText

class ListDetailActivity : AppCompatActivity() {

    lateinit var addTaskButton: FloatingActionButton
    lateinit var listItemsRecyclerView: RecyclerView
    lateinit var list: TaskList

    override fun onBackPressed() {
        val bundle = Bundle()
        // Here's where I wrap my data back to my activity in order to save it?
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        addTaskButton = findViewById<FloatingActionButton>(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)
        title = list.name

        listItemsRecyclerView = findViewById(R.id.list_items_recyclerview)
        listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(list)
        listItemsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    // Modal to add new items.
    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText) // You can assign view that were created imperatively
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                val task = taskEditText.text.toString()
                list.tasks.add(task)

                val recyclerViewAdapter = listItemsRecyclerView.adapter as
                        ListItemsRecyclerViewAdapter
                recyclerViewAdapter.notifyItemInserted(list.tasks.size)

                dialog.dismiss()
            }
            .create()
            .show()
    }
}
