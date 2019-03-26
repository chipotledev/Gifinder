package io.chipotie.gifinder.model

import com.google.gson.annotations.SerializedName

/*
 * @author savirdev on 25/03/19
 */

data class PreviewGif(

    @SerializedName("url")
    val url: String,

    @SerializedName("width")
    val width: String,

    @SerializedName("height")
    val height: String,

    @SerializedName("size")
    val size: String
)