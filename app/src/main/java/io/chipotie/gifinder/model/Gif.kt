package io.chipotie.gifinder.model

import com.google.gson.annotations.SerializedName

/*
 * @author savirdev on 25/03/19
 */

data class Gif(

    @SerializedName("type")
    val type : String,

    @SerializedName("id")
    val id : String,

    @SerializedName("url")
    val url : String,

    @SerializedName("username")
    val username : String,

    @SerializedName("rating")
    val rating : String,

    @SerializedName("user")
    val user : User,

    @SerializedName("images")
    val images: Image,

    @SerializedName("String")
    val title: String

)