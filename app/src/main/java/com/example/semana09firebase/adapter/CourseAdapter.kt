package com.example.semana09firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semana09firebase.R
import com.example.semana09firebase.model.CourseModel
import com.squareup.picasso.Picasso

class CourseAdapter(private var lstCourses: List<CourseModel>): RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvScore: TextView = itemView.findViewById(R.id.tvScore)
        val ivNota: ImageView = itemView.findViewById(R.id.ivNota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_course, parent, false))
    }

    override fun getItemCount(): Int {
        return lstCourses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCourse = lstCourses[position]
        holder.tvDescription.text = itemCourse.description
        holder.tvScore.text = itemCourse.score
        Picasso.get().load(itemCourse.imageUrl).into(holder.ivNota)
    }
}