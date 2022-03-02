package com.chopchop.calambur.dialogs.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chopchop.calambur.R
import com.chopchop.calambur.swipe.SwipeFragment

class DialogFragment : Fragment(R.layout.activity_dialogs){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.activity_dialogs, null)

        val dialogResView = v.findViewById<RecyclerView>(R.id.dialogResView)
        dialogResView.adapter = DialogListAdapter(SwipeFragment().createQuestionaryEntity())
        dialogResView.layoutManager = LinearLayoutManager(v.context,LinearLayoutManager.VERTICAL,false)

        val randomDialogResView = v.findViewById<RecyclerView>(R.id.randomDialogResView)
        randomDialogResView.adapter = RandomDialogAdapter(SwipeFragment().createQuestionaryEntity())
        randomDialogResView.layoutManager = LinearLayoutManager(v.context,LinearLayoutManager.HORIZONTAL,false)
        return v
    }
}