package com.example.semana09firebase.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semana09firebase.R
import com.example.semana09firebase.adapter.CourseAdapter
import com.example.semana09firebase.model.CourseModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore


class ListadoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_listado, container, false)

        val db = FirebaseFirestore.getInstance()
        var lstCourses: List<CourseModel>
        val rvCourse: RecyclerView = view.findViewById(R.id.rvCourse)

        db.collection("courses")
            .addSnapshotListener { snap, e ->
                if (e != null) {
                    Log.i("ERROR", "Ocurrió un error")
                    return@addSnapshotListener
                }

                lstCourses = snap!!.documents.map { document ->
                    CourseModel(
                        document["description"].toString(),
                        document["score"].toString(),
                        document["imageUrl"].toString()
                    )
                }

                rvCourse.adapter = CourseAdapter(lstCourses)
                rvCourse.layoutManager = LinearLayoutManager(requireContext())
            }
        return view
    }
}