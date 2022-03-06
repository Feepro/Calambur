package com.chopchop.calambur.entity


data class ProfileEntity(
    var id: String? = null,
    var name: String? = null,
    var city: String? = null,
    var age:String? = null,
    var avatarUrl: String? = null,
    var description:String? = null,
    var calambur:String? = null,
    var sex:Boolean? = null,

): java.io.Serializable