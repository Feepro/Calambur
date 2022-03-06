package com.chopchop.calambur.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chopchop.calambur.R
import com.chopchop.calambur.entity.ProfileEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

//import kotlinx.serialization.json.Json
//import kotlinx.serialization.decodeFromString

class EditProfileActivity : AppCompatActivity() {

    lateinit var name           : EditText
    lateinit var city           : EditText
    lateinit var age            : EditText
    lateinit var avatar      : ImageView
    lateinit var description    : EditText
    lateinit var calambur       : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initActivity()

        if(intent.getStringExtra("profile") != null){
            fillActivity()
        }
    }

    private fun fillActivity() {
        val profile = Json.decodeFromString<ProfileEntity>(intent.getStringExtra("profile").toString())
        name.setText(profile.name)
        city.setText(profile.city)
        age.setText(profile.age.toString())
        Glide.with(avatar)
            .load(profile.avatarUrl)
            .into(avatar)
        description.setText(profile.description)
        calambur.setText(profile.calambur)
    }
    private fun initActivity() {
        name = findViewById(R.id.nameET)
        city = findViewById(R.id.cityET)
        age = findViewById(R.id.ageET)
        avatar = findViewById(R.id.avatarIV)
        description = findViewById(R.id.descriptionET)
        calambur = findViewById(R.id.calamburET)
    }
}