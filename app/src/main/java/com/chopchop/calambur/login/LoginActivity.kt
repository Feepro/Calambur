package com.chopchop.calambur.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chopchop.calambur.MainActivity
import com.chopchop.calambur.R
import com.chopchop.calambur.profile.EditProfileActivity
import com.chopchop.calambur.registration.RegistrationCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser!=null)
            Log.i("TAG", "onStart: USER ALREADY LOGIN")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

// ...
// Initialize Firebase Auth
        auth = Firebase.auth

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            auth.signInWithEmailAndPassword(
                findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString(),
                findViewById<EditText>(R.id.editTextTextPassword).text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        //Log.d(TAG, "onCreate: "+user!!.uid)
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            startActivity(Intent(this,RegistrationCase::class.java))
        }
    }
}