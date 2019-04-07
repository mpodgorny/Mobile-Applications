package com.example.todo

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.View
import android.widget.*
import java.util.*
import kotlinx.android.synthetic.main.dialog_add_task.*
import kotlin.collections.HashMap
import android.widget.AdapterView
import android.view.MenuInflater







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
        task.put("image", "home")
        tasks.add(task)
        task.put("title", "Zadanie testowe numer 2")
        task.put("priority", "1.5")
        task.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task.put("date", "26.12.20121")
        task.put("image", "university")

        tasks.add(task)



        adapter = taskAdapter(this, tasks)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listview)
        listView.adapter = adapter



        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { arg0, arg1, pos, arg3 ->

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Remove task?")

            builder.setPositiveButton("Remove") { dialogInterface, i ->
                tasks.removeAt(pos)
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("Cancel") { dialogInterface, i ->
                Toast.makeText(applicationContext,"Canceled.",Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
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

        val image = dialogLayout.findViewById<ImageView>(R.id.newphoto)
        var imageChosen = "home"
        image.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this,image)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.home ->{
                        imageChosen = "home"
                        image.setImageResource(R.drawable.home)
                    }
                    R.id.university ->{
                        imageChosen = "university"
                        image.setImageResource(R.drawable.company)
                    }
                    R.id.programming ->{
                        imageChosen = "programming"
                        image.setImageResource(R.drawable.laptop)
                    }
                    R.id.interpersonal ->{
                        imageChosen = "interpersonal"
                        image.setImageResource(R.drawable.customer)
                    }
                }
                true
            })
            popupMenu.show()
        }

        var task = HashMap<String, Any>()
        builder.setView(dialogLayout)

        builder.setPositiveButton("OK") {
                dialogInterface, i ->
            task.put("title", title.text)
            task.put("priority", rate.getRating())
            task.put("date", date.text)
            task.put("description", description.text)
            task.put("image", imageChosen)
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
