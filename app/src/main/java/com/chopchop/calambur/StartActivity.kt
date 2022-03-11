package com.chopchop.calambur

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val db = Firebase.firestore
        val result = db.collection("profiles")
            .whereEqualTo("address_name","городской округ Батайск").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("123", "123 ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        val resulttt = db.collection("profiles")
            .whereEqualTo("address_state","Ростовская область")
            .whereNotEqualTo("address_name","городской округ Батайск")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("123", "123 ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
//        val r = db.collection("profiles")
//            .orderBy("city").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
    }




}