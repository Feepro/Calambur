package com.chopchop.calambur

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.login.LoginActivity
import com.chopchop.calambur.profile.MyProfileRequestFirebase
import com.chopchop.calambur.profile.ProfileRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser!=null){
            MyProfileRequestFirebase().getMyProfile(currentUser.uid,object :ProfileRequest {
                override fun OnSuccess(result: ProfileEntity) {
                    val intent = Intent(this@StartActivity,MainActivity::class.java)
                    intent.putExtra("myProfile", Json.encodeToString(result))
                    startActivity(intent)
                    finish()
                }

                override fun OnError(e: Exception) {
                    startActivity(Intent(this@StartActivity,LoginActivity::class.java))
                    finish()
                }

            })
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val db = Firebase.firestore

//        val result = db.collection("profiles")
//            .whereEqualTo("address_name","городской округ Батайск").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("123", "123 ${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//        val resulttt = db.collection("profiles")
//            .whereEqualTo("address_state","Ростовская область")
//            .whereNotEqualTo("address_name","городской округ Батайск")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("123", "123 ${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
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