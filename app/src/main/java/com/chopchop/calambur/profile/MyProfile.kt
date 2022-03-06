package com.chopchop.calambur.profile

import android.content.ContentValues.TAG
import android.util.Log
import com.chopchop.calambur.entity.ProfileEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MyProfile : MyProfileDAO {

    override fun createProfile(profile: ProfileEntity) {
        val db = Firebase.firestore
        // Create a new user with a first and last name
        val user = hashMapOf(
            "age" to profile.age,
            "calambur" to profile.calambur,
            "description" to profile.description,
            "name" to profile.name,
            "avatarUrl" to profile.avatarUrl,
            "city" to profile.city,
            "sex" to profile.sex
        )

// Add a new document with a generated ID
        db.collection("profiles")
            .document(profile.id.toString())
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${profile.id}")

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
    fun createProfile(profile:ProfileEntity)
    fun updateProfile(updatedProfile: ProfileEntity)
    fun deleteProfile(profile: ProfileEntity)
}