package com.chopchop.calambur.swipe

import android.content.ContentValues
import android.util.Log
import com.chopchop.calambur.entity.ProfileEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileRequest {
    fun getProfilesForSwipe(
        myProfile:ProfileEntity,
        limit:Int,
        onlyCity:Boolean,
    ): ArrayList<ProfileEntity>?{
        val db = Firebase.firestore
        val result = ArrayList<ProfileEntity>()
        db.collection("profiles")
            .whereEqualTo("address_name",myProfile.address_name)
            .limit(limit.toLong()).get()
            .addOnSuccessListener { resultQuery ->
                for (document in resultQuery) {
                    Log.d("123", "123 ${document.id} => ${document.data}")
                    result.add(getProfileEntitiesFromSnapshot(document.data, document.id))
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        if(!onlyCity)
            db.collection("profiles")
                .whereEqualTo("address_state",myProfile.address_state)
                .whereNotEqualTo("address_name",myProfile.address_name)
                .limit(limit.toLong()-result.size).get()
                .addOnSuccessListener { resultQuery ->
                    for (document in resultQuery) {
                        Log.d("123", "123 ${document.id} => ${document.data}")
                        result.add(getProfileEntitiesFromSnapshot(document.data,document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
        return result
    }

    private fun getProfileEntitiesFromSnapshot(data: Map<String, Any>, id: String):ProfileEntity{
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