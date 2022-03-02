package com.chopchop.calambur

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import com.chopchop.calambur.entity.ProfileEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyProfile :MyProfileDAO{
    override fun createProfile(/*profile: ProfileEntity*/) {
        val db = Firebase.firestore
        // Create a new user with a first and last name
        val user = hashMapOf(
            "age" to 1,
            "calambur" to "calamburrrrrr",
            "description" to "descriptionnn",
            "name" to "nameeeeee",
            "avatarUrl" to "avatarUrlllll",
            "city" to "cityyyyyy",
            "sex" to false
        )

// Add a new document with a generated ID
        db.collection("profiles")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun updateProfile(updatedProfile: ProfileEntity) {

    }

    override fun deleteProfile(profile: ProfileEntity) {

    }
}
interface MyProfileDAO{
    fun createProfile(/*profile:ProfileEntity*/)
    fun updateProfile(updatedProfile: ProfileEntity)
    fun deleteProfile(profile: ProfileEntity)
}