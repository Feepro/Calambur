package com.chopchop.calambur.entity

data class ProfileEntity(
    val id: Long = counter++,
    val name: String,
    val city: String,
    val age:Int,
    val avatarUrl: String,
    val description:String,
    val calambur:String,

) {
    companion object {
        private var counter = 0L
    }
}