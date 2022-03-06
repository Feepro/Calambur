package com.chopchop.calambur.registration

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.TextView
import com.chopchop.calambur.R
import android.animation.ObjectAnimator
import android.view.animation.AnimationUtils


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findViewById<TextView>(R.id.startRegBtn).setOnClickListener {
            val  anim = AnimationUtils.loadAnimation(this, R.anim.shake_btn);
            it.startAnimation(anim)
            it.animation.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationEnd(p0: Animation?) {
                    startActivity(Intent(this@WelcomeActivity, RegistrationCase::class.java))
                }
                override fun onAnimationRepeat(p0: Animation?) {}
                override fun onAnimationStart(p0: Animation?) {}
            })
        }
    }
}