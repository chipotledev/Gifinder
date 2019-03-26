package io.chipotie.gifinder.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
 * @author savirdev on 25/03/19
 */

@Parcelize
data class Image(
    @SerializedName("preview_gif")
    val previewGif: PreviewGif,

    @SerializedName("original")
    val originalGif: OriginalGif,

    @SerializedName("fixed_height")
    val downsizedLargeGif: DownsizedLargeGif
) : Parcelable