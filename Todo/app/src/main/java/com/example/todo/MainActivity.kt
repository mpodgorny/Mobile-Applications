package com.example.todo

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.View
import android.widget.*
import java.util.*
import kotlin.collections.HashMap
import android.widget.AdapterView
import android.view.MenuItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    var tasks = ArrayList<HashMap<String, String>>()
    private lateinit var listView : ListView
    private lateinit var adapter : taskAdapter

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("tasks", tasks)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            tasks = savedInstanceState.getSerializable("tasks") as ArrayList<HashMap<String, String>>
        }
        createAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createAdapter()

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
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        date.text="$formatted"
        date.setOnClickListener{
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var tempDate="$dayOfMonth-$monthOfYear-$year"
            if(dayOfMonth<10 && monthOfYear<10){
                tempDate="0$dayOfMonth-0$monthOfYear-$year"
            }else if(dayOfMonth<10){
                tempDate="0$dayOfMonth-$monthOfYear-$year"
            }else if(monthOfYear<10){
                tempDate="$dayOfMonth-0$monthOfYear-$year"
            }
            date.text=tempDate
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

        builder.setPositiveButton("Add") {
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
            R.id.random -> {
                addRandomTask()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun addRandomTask(){
        var charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        var months = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        var pictures = listOf("home", "university", "programming", "interpersonal")
        val randomTitle = (1..15)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        val randomText = (1..100)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        var task = HashMap<String, String>()
        val year = kotlin.random.Random.nextInt(2019,2021)
        val month = months[kotlin.random.Random.nextInt(0,12)]
        var idx = kotlin.random.Random.nextInt(1, 31)
        val pic = pictures[kotlin.random.Random.nextInt(0, 4)]
        if(idx<10){
            task.put("date", "0$idx-$month-$year")
        }else{
            task.put("date", "$idx-$month-$year")
        }
        task.put("title", randomTitle)
        task.put("description", randomText)
        task.put("image", pic)
        task.put("priority", kotlin.random.Random.nextInt(0,5).toString())
        tasks.add(task)
        adapter.notifyDataSetChanged()
    }

    fun createAdapter(){

        adapter = taskAdapter(this, tasks)

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
}
