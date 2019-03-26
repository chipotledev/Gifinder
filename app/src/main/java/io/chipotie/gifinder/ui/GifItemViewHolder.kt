package io.chipotie.gifinder.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.chipotie.gifinder.R
import io.chipotie.gifinder.model.Gif
import kotlinx.android.synthetic.main.item_gif.view.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*


/*
 * @author savirdev on 22/03/19
 */

class GifItemViewHolder(view: View, private val callback:ListCallback, private val context: Context) : RecyclerView.ViewHolder(view){

    fun bind(gif: Gif?) {
        if (gif != null) {

            Glide.with(context)
                .load(gif.images.previewGif.url)
                .placeholder(ColorDrawable(Color.LTGRAY))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.iv_gif)

            itemView.container.setOnClickListener{
                callback.onSelectedItem(gif)
            }
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    companion object {
        fun create(callback: ListCallback, parent: ViewGroup, context: Context): GifItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gif, parent, false)
            return GifItemViewHolder(view,callback,context)
        }
    }
}