package io.chipotie.gifinder.model

import com.google.gson.annotations.SerializedName

/*
 * @author savirdev on 25/03/19
 */

data class User(

    @SerializedName("avatar_url")
    val avatarUrl : String,

    @SerializedName("banner_url")
    val bannerUrl : String,

    @SerializedName("banner_image")
    val bannerImage : String,

    @SerializedName("profile_url")
    val profileUrl : String,

    @SerializedName("username")
    val username : String,

    @SerializedName("display_name")
    val displayName : String,

    @SerializedName("is_verifie")
    val isVerified : Boolean
)