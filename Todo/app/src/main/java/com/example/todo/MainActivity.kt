package com.example.todo

import android.app.DatePickerDialog
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
import android.view.MenuItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    var tasks = ArrayList<HashMap<String, String>>()
    private lateinit var listView : ListView
    private lateinit var adapter : taskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        addTestTasks()

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


        return super.onCreateOptionsMenu(menu)
    }
    fun onAdd (View: View){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Task")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_task, null)

        val title  = dialogLayout.findViewById<EditText>(R.id.newtitle)
        val description  = dialogLayout.findViewById<EditText>(R.id.newdescription)
        val rate  = dialogLayout.findViewById<RatingBar>(R.id.newrating)

        val date  = dialogLayout.findViewById<TextView>(R.id.date)
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = current.format(formatter)
        date.text="$formatted"
        date.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                date.text="$dayOfMonth.$monthOfYear.$year"
            }, year, month, day)
            dpd.show()

        }



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

        var task = HashMap<String, String>()
        builder.setView(dialogLayout)

        builder.setPositiveButton("OK") {
                dialogInterface, i ->
            task.put("title", title.text.toString())
            task.put("priority", rate.getRating().toString())
            task.put("date", date.text.toString())
            task.put("description", description.text.toString())
            task.put("image", imageChosen.toString())
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.priority -> {
                var mapCompare = MapComparator("priority")
                Collections.sort(tasks, mapCompare)
                adapter.notifyDataSetChanged()
                return true
            }
            R.id.deadline -> {
                var mapCompare = MapComparator("date")
                Collections.sort(tasks, mapCompare)
                adapter.notifyDataSetChanged()
                return true
            }
            R.id.photo -> {
                var mapCompare = MapComparator("image")
                Collections.sort(tasks, mapCompare)
                adapter.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addTestTasks(){
        var task = HashMap<String, String>()
        task.put("title", "Zadanie testowe")
        task.put("priority", "4")
        task.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task.put("date", "22.4.2019")
        task.put("image", "home")
        tasks.add(task)
        var task2 = HashMap<String, String>()
        task2.put("title", "Zadanie testowe numer 2")
        task2.put("priority", "1")
        task2.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task2.put("date", "21.4.2019")
        task2.put("image", "university")
        tasks.add(task2)

        var task3 = HashMap<String, String>()
        task3.put("title", "Zadanie testowe numer 3")
        task3.put("priority", "5")
        task3.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task3.put("date", "29.4.2019")
        task3.put("image", "home")
        tasks.add(task3)

        var task4 = HashMap<String, String>()
        task4.put("title", "Zadanie testowe numer 4")
        task4.put("priority", "4")
        task4.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task4.put("date", "23.4.2019")
        task4.put("image", "interpersonal")
        tasks.add(task4)

        var task5 = HashMap<String, String>()
        task5.put("title", "Zadanie testowe numer 5")
        task5.put("priority", "3")
        task5.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task5.put("date", "25.7.2019")
        task5.put("image", "programming")
        tasks.add(task5)

        var task6 = HashMap<String, String>()
        task6.put("title", "Zadanie testowe numer 6")
        task6.put("priority", "2")
        task6.put("description", "Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie Bardzo dlugie zadanie ")
        task6.put("date", "5.5.2019")
        task6.put("image", "university")
        tasks.add(task6)

    }

}
