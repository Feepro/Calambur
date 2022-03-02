package com.chopchop.calambur.dialogs.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chopchop.calambur.R
import com.chopchop.calambur.entity.ProfileEntity

class RandomDialogAdapter   (
    private var dialogList: List<ProfileEntity> = emptyList()
) : RecyclerView.Adapter<RandomDialogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.random_dialog_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = dialogList[position]
        Glide.with(holder.image)
            .load(spot.avatarUrl)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }
        if (position%2 == 0)
            holder.onlineIco.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return dialogList.size
    }

    fun setSpots(spots: List<ProfileEntity>) {
        this.dialogList = spots
    }

    fun getSpots(): List<ProfileEntity> {
        return dialogList
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.avatar)
        val onlineIco : ImageView = view.findViewById(R.id.onlineIco)
    }

}