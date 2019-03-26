package io.chipotie.gifinder.model

import com.google.gson.annotations.SerializedName

/*
 * @author savirdev on 25/03/19
 */

data class Image(
    @SerializedName("preview_gif")
    val previewGif: PreviewGif
)