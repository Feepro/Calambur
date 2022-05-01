package com.chopchop.calambur.profile

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.chopchop.calambur.entity.ProfileEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MyProfileRequestFirebase : MyProfileDAO {
    override fun getMyProfile(uid: String,listener:ProfileRequest):ProfileEntity {
        var result = ProfileEntity()
        val db = Firebase.firestore
        db.collection("profiles")
            .document(uid)
            .get()
            .addOnSuccessListener { resultQuery ->
                result = getProfileEntitiesFromSnapshot(resultQuery.data!!, uid)
                listener.OnSuccess(result)
            }
            .addOnFailureListener {
                listener.OnError(it)
                Log.e(ContentValues.TAG, "Error getting documents.", it)
            }
        return result
    }


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
            "sex" to profile.sex,
            "address_name" to profile.address_name,
            "address_state" to profile.address_state
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

    fun getProfileEntitiesFromSnapshot(data: Map<String, Any>, id: String):ProfileEntity{
        return ProfileEntity(
            id = id,
            name = data["name"] as String?,
            city = data["city"] as String?,
            age = data["age"] as String?,
            avatarUrl = data["avatarUrl"] as String?,
            description = data["description"] as String? ,
            calambur = data["calambur"] as String?,
            sex = data["sex"] as Boolean?,
            address_name = data["address_name"] as String? ,
            address_state = data["address_state"] as String?,
        )
    }
}
interface MyProfileDAO{
    fun getMyProfile(uid:String,listener:ProfileRequest):ProfileEntity
    fun createProfile(profile:ProfileEntity)
    fun updateProfile(updatedProfile: ProfileEntity)
    fun deleteProfile(profile: ProfileEntity)
}
interface ProfileRequest{
    fun OnSuccess(result: ProfileEntity)
    fun OnError(e:Exception)
}