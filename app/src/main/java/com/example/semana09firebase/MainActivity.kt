package com.example.semana09firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()
        val tvCurso: TextView = findViewById(R.id.tvCurso)
        val tvNota: TextView = findViewById(R.id.tvNota)
        db.collection("courses")
            .addSnapshotListener{ snapshosts, e ->

                if(e!=null){
                    Snackbar
                        .make(
                            findViewById(android.R.id.content)
                            ,"Ocurrió un error al consultar la colección"
                            ,Snackbar.LENGTH_LONG
                        ).show()
                    return@addSnapshotListener
                }

                for(dc in snapshosts!!.documentChanges)
                {
                    when(dc.type){
                        DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED ->{
                            Snackbar
                                .make(
                                    findViewById(android.R.id.content)
                                    ,"Se agregó un documento"
                                    ,Snackbar.LENGTH_LONG
                                ).show()
                            tvCurso.text = dc.document.data["description"].toString()
                            tvNota.text = dc.document.data["score"].toString()

                        }
                        DocumentChange.Type.REMOVED -> {
                            Snackbar
                                .make(
                                    findViewById(android.R.id.content)
                                    ,"Se eliminó el documento"
                                    ,Snackbar.LENGTH_LONG
                                ).show()
                        }
                        else -> {
                            Snackbar
                                .make(
                                    findViewById(android.R.id.content)
                                    ,"Error al consultar la colección"
                                    ,Snackbar.LENGTH_LONG
                                ).show()
                        }
                    }
                }
            }
    }
}