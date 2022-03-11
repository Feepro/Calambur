package com.chopchop.calambur.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.view.children
import com.chopchop.calambur.MainActivity
import com.chopchop.calambur.R
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.profile.MyProfileRequestFirebase
import com.google.firebase.auth.FirebaseAuth
import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.InputStream


class RegistrationCase : AppCompatActivity() {
    var stageCompleted = true
    val myProfile= ProfileEntity()
    var stageIndex = 0
    var imageLoaded = false
    private lateinit var stage:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reg_stage)

        stage = findViewById<LinearLayout>(R.id.stage1)

        findViewById<TextView>(R.id.next_stage_reg).setOnClickListener {


            if(imageLoaded){
                MyProfileRequestFirebase().createProfile(profile = myProfile)
                startActivity(Intent(applicationContext,MainActivity::class.java))
                finish()
            }
            nextStage()
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
                myProfile.name = (stage.children.elementAt(2) as EditText).text.toString().replace("\\s".toRegex(), "")
                if(myProfile.name== "" || myProfile.name== null){
                    stageCompleted = false
                    stage
                }else{
                    stageCompleted = true
                    findViewById(R.id.stage2)
                }
            }
            2->{
                myProfile.sex = ((stage.children.elementAt(2) as RadioGroup).children.elementAt(0) as RadioButton).isChecked
                stageCompleted = true
                findViewById(R.id.stage3)
            }
            3->{
                myProfile.age = (stage.children.elementAt(2) as EditText).text.toString().replace("\\s".toRegex(), "")
                if(myProfile.age== "" || myProfile.age== null){
                    stageCompleted = false
                    stage
                }else{
                    stageCompleted = true
                    findViewById(R.id.stage4)
                }
            }
            4->{
                myProfile.city = (stage.children.elementAt(2) as EditText).text.toString().replace("\\s".toRegex(), "")
                if(myProfile.city== "" || myProfile.city== null){
                    stageCompleted = false
                    stage
                }else{
                    runBlocking(Dispatchers.IO) {
                        val geo = GetGeoCoding().getGeoCoding(myProfile.city!!)
                      if(geo !=null){
                          myProfile.address_name = geo.name
                          myProfile.address_state = geo.state
                      }
                    }
                    stageCompleted = true

                    findViewById(R.id.stage5)
                }
            }
            5->{
                myProfile.description = (stage.children.elementAt(2) as EditText).text.toString()
                if(myProfile.description== "" || myProfile.description== null){
                    stageCompleted = false
                    stage
                }else{
                    stageCompleted = true
                    findViewById(R.id.stage7)
                }

            }
            6->{
                val firebaseAuth = FirebaseAuth.getInstance()
                val login = (stage.children.elementAt(2) as EditText).text.toString()
                val pass = (stage.children.elementAt(3) as EditText).text.toString()
                val passAgain = (stage.children.elementAt(4) as EditText).text.toString()
                if(pass != passAgain||login.isBlank() || pass.isBlank()||passAgain.isBlank()) {
                    Toast.makeText(
                        this@RegistrationCase,
                        "Данные недействительны",
                        Toast.LENGTH_SHORT
                    ).show()
                    stageCompleted = false
                    stage
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(login, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@RegistrationCase,
                                    "created account successfully !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                myProfile.id = task.result!!.user!!.uid
                                stageCompleted = true
                                stage.visibility = View.INVISIBLE
                                stage = findViewById(R.id.stage6)
                                findViewById<FloatingActionButton>(R.id.selectAvatar).setOnClickListener {
                                    imageLoaded=false
                                    val intent = Intent()
                                    intent.type = "image/*"
                                    intent.action = Intent.ACTION_GET_CONTENT
                                    startActivityForResult(
                                        Intent.createChooser(intent, "Select Picture"),
                                        1
                                    )
                                }
                                slideNewStage()
                                stageCompleted = false
                            } else {
                                Toast.makeText(
                                    this@RegistrationCase,
                                    "failed to Authenticate !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                stageCompleted = false
                                stage.visibility = View.INVISIBLE
                                stage = findViewById(R.id.stage7)
                                slideNewStage()

                            }
                        }
                    findViewById(R.id.loading)

                }
            }
            else -> {
                    if(!imageLoaded)
                        findViewById(R.id.stage6)
                    else
                        findViewById(R.id.loading)


            }
        }
        slideNewStage()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null) {
            val inputStream: InputStream = this.getContentResolver().openInputStream(data.data!!)!!
            val bitmap = BitmapFactory.decodeStream(inputStream)
            findViewById<ImageView>(R.id.avatarIV).setImageBitmap(bitmap)
            val storage = FirebaseStorage.getInstance()

            stage = findViewById(R.id.loading)
            slideNewStage()
            storage.getReference("avatar/${myProfile.id}.jpg").putFile(data.data!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@RegistrationCase,
                        "Фото загружено!",
                        Toast.LENGTH_SHORT
                    ).show()
                    myProfile.avatarUrl = "gs://calambur-e81bb.appspot.com/avatar/${myProfile.id}.jpg"
                    stageCompleted = true
                    imageLoaded = true
                    stage.visibility = View.INVISIBLE
                    stage = findViewById(R.id.emptyStage)
                    slideNewStage()
                } else {
                    Log.e(TAG, "onActivityResult: "+task.exception )
                    Toast.makeText(
                        this@RegistrationCase,
                        "Проверьте соединение!",
                        Toast.LENGTH_SHORT
                    ).show()
                    stageCompleted = false
                    imageLoaded= false
                    stage = findViewById(R.id.stage6)
                    slideNewStage()
                }
            }
        }
    }
    fun slideNewStage(){
        stage.startAnimation(AnimationUtils.loadAnimation(this@RegistrationCase,R.anim.slide_from_right))
        stage.visibility = View.VISIBLE
        if(stageCompleted)
            stageIndex++

        //stageCompleted = false TODO
    }
}