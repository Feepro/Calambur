package com.chopchop.calambur.dialogs.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chopchop.calambur.R
import com.chopchop.calambur.entity.ProfileEntity

class DialogListAdapter   (
    private var dialogList: List<ProfileEntity> = emptyList()
) : RecyclerView.Adapter<DialogListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_dialog, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = dialogList[position]
        holder.name.text = "${spot.id}. ${spot.name}"
        holder.lastMessage.text = spot.city
        Glide.with(holder.image)
            .load(spot.avatarUrl)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }
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
        val name: TextView = view.findViewById(R.id.dialogName)
        val image: ImageView = view.findViewById(R.id.dialogAvatar)
        val lastMessage:TextView = view.findViewById(R.id.dialogLastMessage)
        val onlineIco : ImageView = view.findViewById(R.id.dialogOnlineIco)
    }

}