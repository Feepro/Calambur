package com.chopchop.calambur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView


class SwipeFragment : Fragment(R.layout.fragment_swipe) {
    private val adapter by lazy { SwipeAdapter(createQuestionaryEntity()) }
    private val layoutManager by lazy {CardStackLayoutManager(this.context)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_swipe, null)
        val cardStackView = v.findViewById<CardStackView>(R.id.card_stack_view)
        layoutManager.setMaxDegree(-20.0f)

        cardStackView.layoutManager = layoutManager
        cardStackView.adapter = adapter
        return v
    }

    private fun createQuestionaryEntity(): List<QuestionaryEntity> {
        val questionaryEntity = ArrayList<QuestionaryEntity>()
        questionaryEntity.add(QuestionaryEntity(name = "Yasaka Shrine", city = "Kyoto", url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Fushimi Inari Shrine", city = "Kyoto", url = "https://source.unsplash.com/NYyCqdBOKwc/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Bamboo Forest", city = "Kyoto", url = "https://source.unsplash.com/buF62ewDLcQ/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Brooklyn Bridge", city = "New York", url = "https://source.unsplash.com/THozNzxEP3g/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Empire State Building", city = "New York", url = "https://source.unsplash.com/USrZRcRS2Lw/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "The statue of Liberty", city = "New York", url = "https://source.unsplash.com/PeFk7fzxTdk/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Louvre Museum", city = "Paris", url = "https://source.unsplash.com/LrMWHKqilUw/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Eiffel Tower", city = "Paris", url = "https://source.unsplash.com/HN-5Z6AmxrM/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Big Ben", city = "London", url = "https://source.unsplash.com/CdVAUADdqEc/600x800"))
        questionaryEntity.add(QuestionaryEntity(name = "Great Wall of China", city = "China", url = "https://source.unsplash.com/AWh9C-QjhE4/600x800"))
        return questionaryEntity
    }

}