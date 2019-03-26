package io.chipotie.gifinder.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.chipotie.gifinder.R
import io.chipotie.gifinder.databinding.ActivityZoomBinding
import io.chipotie.gifinder.model.Gif
import kotlinx.android.synthetic.main.item_gif.view.*

class ZoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomBinding
    private var gif : Gif? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_zoom)
        binding.view = this
        retrieveGif()
        setupGif()
    }

    private fun retrieveGif(){
        gif = intent?.extras?.getParcelable(TrendingActivity.EXTRA_GIF)
    }

    private fun setupGif(){

        Glide.with(this)
            .load(gif?.images?.downsizedLargeGif?.url)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.ivGift)
    }

    fun closeZoom(){
        onBackPressed()
    }
}
