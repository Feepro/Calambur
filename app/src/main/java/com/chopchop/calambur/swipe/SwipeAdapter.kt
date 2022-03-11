package com.chopchop.calambur.swipe

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chopchop.calambur.entity.ProfileEntity
import com.chopchop.calambur.R
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class SwipeAdapter(
    private var spots: List<ProfileEntity> = emptyList()
) : RecyclerView.Adapter<SwipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_swipe, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        val storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        val pathReference = storage.getReferenceFromUrl(spot.avatarUrl!!)
        holder.name.text = "${spot.name} ${spot.age}. ${spot.city}"
        holder.city.text = spot.address_state
        runBlocking(Dispatchers.IO){
            pathReference.getBytes(9999999).addOnCompleteListener {
                holder.image.setImageBitmap(BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size))
            }
        }

//        Glide.with(holder.image)
//            .load(spot.avatarUrl)
//            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<ProfileEntity>) {
        this.spots = spots
    }

    fun getSpots(): List<ProfileEntity> {
        return spots
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}