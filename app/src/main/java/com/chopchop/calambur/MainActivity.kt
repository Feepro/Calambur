package com.chopchop.calambur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SwipeFragment>(R.id.frgFrame)
            }
        }
        val bottomNavigation = findViewById<MeowBottomNavigation>(R.id.navigationBar)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_baseline_message_24))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_local_fire_department_24))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_baseline_account_circle_24))
        bottomNavigation.show(2,true)
    }
}