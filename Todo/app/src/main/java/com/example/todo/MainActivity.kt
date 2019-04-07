package com.example.todo

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import java.util.*
import kotlinx.android.synthetic.main.dialog_add_task.*
import kotlin.collections.HashMap



class MainActivity : AppCompatActivity() {
    var tasks = ArrayList<HashMap<String, Any>>()
    private lateinit var listView : ListView
    private lateinit var adapter : taskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        var task = HashMap<String, Any>()
        task.put("title", "Zadanie testowe")
        task.put("priority", "4")
        task.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task.put("date", "21.04.2019")
        tasks.add(task)
        task.put("title", "Zadanie testowe numer 2")
        task.put("priority", "1.5")
        task.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task.put("date", "26.12.20121")
        tasks.add(task)



        adapter = taskAdapter(this, tasks)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listview)
        listView.adapter = adapter

    }

    fun onAdd (View: View){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Task")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_task, null)
        val title  = dialogLayout.findViewById<EditText>(R.id.newtitle)
        val description  = dialogLayout.findViewById<EditText>(R.id.newdescription)
        val date  = dialogLayout.findViewById<EditText>(R.id.newdate)
        val rate  = dialogLayout.findViewById<RatingBar>(R.id.newrating)
        var task = HashMap<String, Any>()
        builder.setView(dialogLayout)

        builder.setPositiveButton("OK") {
                dialogInterface, i ->
            task.put("title", title.text)
            task.put("priority", rate.getRating())
            task.put("date", date.text)
            task.put("description", description.text)
            tasks.add(task)
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") {
                dialogInterface, i ->
            Toast.makeText(applicationContext,"Canceled.",Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
