package com.chopchop.calambur.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.chopchop.calambur.MainActivity
import com.chopchop.calambur.R
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.profile.MyProfile
import com.google.firebase.auth.FirebaseAuth

class RegistrationCase : AppCompatActivity() {
    var stageCompleted = true
    val myProfile= ProfileEntity()
    var stageIndex = 0
    private lateinit var stage:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reg_stage)

        stage = findViewById<LinearLayout>(R.id.stage1)

        findViewById<TextView>(R.id.next_stage_reg).setOnClickListener {


            //TODO Сделать проверки на то что юзер действительно заполнил все поля
            if(stageCompleted){
                nextStage()
            }
        }

    }
    fun nextStage(){
        if(stageIndex == 0) {
            stage.visibility = View.VISIBLE
            initNewStage()
            return
        }
        stage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_to_left))
        stage.animation.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationEnd(p0: Animation?) {
                    stage.visibility = View.INVISIBLE
                    initNewStage()
                }
                override fun onAnimationRepeat(p0: Animation?) {}
                override fun onAnimationStart(p0: Animation?) {}
            })

    }
    fun initNewStage(){
        stage = when(stageIndex){
            0->{
                findViewById(R.id.stage1)
            }
            1->{
                myProfile.name = (stage.children.elementAt(2) as EditText).text.toString()
                findViewById(R.id.stage2)
            }
            2->{
                myProfile.sex = ((stage.children.elementAt(2) as RadioGroup).children.elementAt(0) as RadioButton).isChecked
                findViewById(R.id.stage3)
            }
            3->{
                myProfile.age = (stage.children.elementAt(2) as EditText).text.toString()

                findViewById(R.id.stage4)
            }
            4->{
                myProfile.city = (stage.children.elementAt(2) as EditText).text.toString()
                findViewById(R.id.stage5)
            }
            5->{
                myProfile.description = (stage.children.elementAt(2) as EditText).text.toString()
                //TODO заливать фото на серв
                findViewById(R.id.stage6)
            }
            6->{
                myProfile.avatarUrl = ((stage.children.elementAt(2) as CardView).children.elementAt(1) as ImageView).transitionName
                findViewById(R.id.stage7)
            }
            7->{
                val firebaseAuth = FirebaseAuth.getInstance()
                val login = (stage.children.elementAt(2) as EditText).text.toString()
                val pass = (stage.children.elementAt(3) as EditText).text.toString()
                val passAgain = (stage.children.elementAt(4) as EditText).text.toString()
                if(pass != passAgain)
                    Toast.makeText(this@RegistrationCase,"Пароли должны совпадать :с",Toast.LENGTH_SHORT)
                else
                    firebaseAuth.createUserWithEmailAndPassword(login, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@RegistrationCase,"created account successfully !",Toast.LENGTH_SHORT)
                                myProfile.id = task.result!!.user!!.uid
                                MyProfile().createProfile(profile = myProfile)
                                startActivity(Intent(applicationContext,MainActivity::class.java))
                            } else {
                                Toast.makeText(this@RegistrationCase,"failed to Authenticate !",Toast.LENGTH_SHORT)
                            }
                        }

                findViewById(R.id.emptyStage)
            }
            else -> {
                findViewById(R.id.emptyStage)
            }
        }
        slideNewStage()
    }
    fun slideNewStage(){
        stage.startAnimation(AnimationUtils.loadAnimation(this@RegistrationCase,R.anim.slide_from_right))
        stage.visibility = View.VISIBLE
        stageIndex++

        //stageCompleted = false TODO
    }
}