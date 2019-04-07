package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import kotlinx.android.synthetic.main.task_layout.view.*
import org.w3c.dom.Text

class taskAdapter (private val context: Context,
                    private val task: ArrayList<HashMap<String, Any>>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return task.size
    }

    //2
    override fun getItem(position: Int): Any {
        return task[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Get view for row item
        val rowView = inflater.inflate(R.layout.task_layout, parent, false)
        val title = rowView.findViewById(R.id.title) as TextView
        val priority = rowView.findViewById(R.id.ratingBar) as RatingBar
        val date = rowView.findViewById(R.id.date) as TextView
        val image = rowView.findViewById(R.id.imageView) as ImageView
        val description = rowView.findViewById(R.id.content) as TextView

            title.setText(task[position].getValue("title").toString())
            priority.rating=task[position].getValue("priority").toString().toFloat()
            date.setText(task[position].getValue("date").toString())
            description.setText(task[position].getValue("description").toString())

        when(task[position].getValue("image").toString()){
            "home" ->
                image.setImageResource(R.drawable.home)
            "university" ->
                image.setImageResource(R.drawable.company)
            "programming" ->
                image.setImageResource(R.drawable.laptop)
            else ->
                image.setImageResource(R.drawable.customer)
        }


        return rowView
    }


}