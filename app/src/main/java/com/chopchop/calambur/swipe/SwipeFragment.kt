package com.chopchop.calambur.swipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.R
import com.yuyakaido.android.cardstackview.*

import android.animation.ValueAnimator


class SwipeFragment : Fragment(R.layout.fragment_swipe),CardStackListener {
    private val adapter by lazy { SwipeAdapter(createQuestionaryEntity()) }
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
        cardStackView.adapter = adapter

        return v
    }

    fun createQuestionaryEntity(): List<ProfileEntity> {
        val questionaryEntity = ArrayList<ProfileEntity>()
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm nicae!",age = 1,name = "Yasaka Shrine", city = "Kyoto", avatarUrl = "https://source.unsplash.com/Xq1ntWruZQI/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm nasdice!1",age = 12,name = "Fushimi Inari Shrine", city = "Kyoto", avatarUrl = "https://source.unsplash.com/NYyCqdBOKwc/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm nice!2",age = 13,name = "Bamboo Forest", city = "Kyoto", avatarUrl = "https://source.unsplash.com/buF62ewDLcQ/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm niasdce!3",age = 14,name = "Brooklyn Bridge", city = "New York", avatarUrl = "https://source.unsplash.com/THozNzxEP3g/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm nizxcce!4",age = 15,name = "Empire State Building", city = "New York", avatarUrl = "https://source.unsplash.com/USrZRcRS2Lw/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm nice!1",age = 16,name = "The statue of Liberty", city = "New York", avatarUrl = "https://source.unsplash.com/PeFk7fzxTdk/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm nice!",age = 17,name = "Louvre Museum", city = "Paris", avatarUrl = "https://source.unsplash.com/LrMWHKqilUw/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm niaasdacce!",age = 188,name = "Eiffel Tower", city = "Paris", avatarUrl = "https://source.unsplash.com/HN-5Z6AmxrM/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm niasdce!",age = 188,name = "Big Ben", city = "London", avatarUrl = "https://source.unsplash.com/CdVAUADdqEc/600x800"))
        questionaryEntity.add(ProfileEntity(calambur = "cal != cum",description = "mmm niasdce!",age = 175,name = "Great Wall of China", city = "China", avatarUrl = "https://source.unsplash.com/AWh9C-QjhE4/600x800"))
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