package io.chipotie.gifinder.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
 * @author savirdev on 25/03/19
 */

@Parcelize
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

    @SerializedName("images")
    val images: Image,

    @SerializedName("title")
    val title: String

) : Parcelable