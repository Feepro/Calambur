package com.chopchop.calambur.swipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.R
import com.yuyakaido.android.cardstackview.*

import android.animation.ValueAnimator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.chopchop.calambur.profile.MyProfileRequestFirebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.json.Json


class SwipeFragment : Fragment(R.layout.fragment_swipe),CardStackListener {
    val testMyProfile = ProfileEntity(
        id = "1",
        name = "Yasaka Shrine",
        city = "Kyoto",
        age = "1",
        avatarUrl = "https://source.unsplash.com/Xq1ntWruZQI/600x800",
        description = "mmm nicae!",
        calambur = "cal != cum",
        sex = true,
        address_name = "городской округ Батайск",
        address_state = "Ростовская область"
    )

    private val layoutManager by lazy {CardStackLayoutManager(this.context,this)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_swipe, null)
        val cardStackView = v.findViewById<CardStackView>(R.id.card_stack_view)
        val likeBtn = v.findViewById<ImageButton>(R.id.likeBtn)
        val dislikeBtn = v.findViewById<ImageButton>(R.id.dislikeBtn)
        val mailBtn = v.findViewById<ImageButton>(R.id.mailBtn)
        val showSwipeBtn = v.findViewById<ImageView>(R.id.showSwipeBtn)
        val swipeButtonPanel = v.findViewById<LinearLayout>(R.id.swipeButtonPanel)
        val startSwipe = v.findViewById<Button>(R.id.startSwipe)
        swipeButtonPanel.tag = 0
        layoutManager.setMaxDegree(-20.0f)


        likeBtn.setOnClickListener{
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
        dislikeBtn.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
        mailBtn.setOnClickListener {
            cardStackView.rewind()
        }
        showSwipeBtn.setOnClickListener {
            val animator:ValueAnimator
                if(swipeButtonPanel.tag == 0) {
                    animator =ValueAnimator.ofFloat(0f, 500f)
                    swipeButtonPanel.tag = 1
                }else{
                    animator = ValueAnimator.ofFloat(500f, 0f)
                    swipeButtonPanel.tag = 0
                }

            animator.addUpdateListener { animation ->
                swipeButtonPanel!!.translationX = (animation.animatedValue as Float)!!

            }
            animator.interpolator = AccelerateInterpolator()
                animator.start()
        }

        cardStackView.layoutManager = layoutManager
        val profiles = ProfileRequest().getProfilesForSwipe(
            testMyProfile,
            100,
            false
        )



        startSwipe.setOnClickListener {
            if (profiles != null) {
                if(profiles.size != 0){
                    cardStackView.adapter = SwipeAdapter(profiles)
                    v.findViewById<ConstraintLayout>(R.id.swipePanel).visibility = View.VISIBLE
                    it.visibility = View.INVISIBLE
                }else{
                    Toast.makeText(context,"Нету анкет больше :с",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Мы ещё не подгрузили странички :c подождите",Toast.LENGTH_SHORT).show()
            }

        }
        return v
    }

    fun createQuestionaryEntity(): List<ProfileEntity> {
        val questionaryEntity = ArrayList<ProfileEntity>()
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Fushimi Inari Shrine",
            city = "Kyoto",
            age = "12",
            avatarUrl = "https://source.unsplash.com/NYyCqdBOKwc/600x800",
            description = "mmm nasdice!1",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Bamboo Forest",
            city = "Kyoto",
            age = "13",
            avatarUrl = "https://source.unsplash.com/buF62ewDLcQ/600x800",
            description = "mmm nice!2",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Brooklyn Bridge",
            city = "New York",
            age = "14",
            avatarUrl = "https://source.unsplash.com/THozNzxEP3g/600x800",
            description = "mmm niasdce!3",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Empire State Building",
            city = "New York",
            age = "15",
            avatarUrl = "https://source.unsplash.com/USrZRcRS2Lw/600x800",
            description = "mmm nizxcce!4",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "The statue of Liberty",
            city = "New York",
            age = "16",
            avatarUrl = "https://source.unsplash.com/PeFk7fzxTdk/600x800",
            description = "mmm nice!1",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Louvre Museum",
            city = "Paris",
            age = "17",
            avatarUrl = "https://source.unsplash.com/LrMWHKqilUw/600x800",
            description = "mmm nice!",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Eiffel Tower",
            city = "Paris",
            age = "188",
            avatarUrl = "https://source.unsplash.com/HN-5Z6AmxrM/600x800",
            description = "mmm niaasdacce!",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Big Ben",
            city = "London",
            age = "188",
            avatarUrl = "https://source.unsplash.com/CdVAUADdqEc/600x800",
            description = "mmm niasdce!",
            calambur = "cal != cum",
            sex = true,
        ))
        questionaryEntity.add(ProfileEntity(
            id = "1",
            name = "Great Wall of China",
            city = "China",
            age = "175",
            avatarUrl = "https://source.unsplash.com/AWh9C-QjhE4/600x800",
            description = "mmm niasdce!",
            calambur = "cal != cum",
            sex = true,
        ))
        return questionaryEntity
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.i("Tag", "ordinal: ${direction!!.ordinal}")
        Log.i("Tag", "name: ${direction!!.name}")
    }

    override fun onCardRewound() {
        
    }

    override fun onCardCanceled() {
        
    }

    override fun onCardAppeared(view: View?, position: Int) {
        
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        
    }

}