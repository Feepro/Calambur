package com.chopchop.calambur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.chopchop.calambur.dialogs.list.DialogFragment
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.profile.ProfileFragment
import com.chopchop.calambur.swipe.SwipeFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val myProfile = Json.decodeFromString<ProfileEntity>(intent.getStringExtra("myProfile").toString())

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SwipeFragment>(R.id.frgFrame)
            }

        }
        initBottomNavigationBar()

    }

    private fun initBottomNavigationBar() {
        val bottomNavigation = findViewById<MeowBottomNavigation>(R.id.navigationBar)

        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_baseline_message_24))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_local_fire_department_24))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_baseline_account_circle_24))
        bottomNavigation.setOnClickMenuListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            when(it.id){
                1->{
                    ft.setCustomAnimations(R.animator.slide_in_top, R.animator.slide_in_left)
                    ft.replace(R.id.frgFrame, DialogFragment(), "mainFragment")
                }
                2->{
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.frgFrame, SwipeFragment(), "mainFragment")
                }
                3->{
                    ft.setCustomAnimations(R.animator.slide_in_top, R.animator.slide_in_right)
                    ft.replace(R.id.frgFrame, ProfileFragment(), "mainFragment")
                }
            }

            ft.addToBackStack(null)
            ft.commit()
        }
        bottomNavigation.show(2,true)
    }
}