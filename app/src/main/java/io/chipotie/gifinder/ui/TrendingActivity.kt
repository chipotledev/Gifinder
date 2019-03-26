package io.chipotie.gifinder.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.chipotie.gifinder.R

class TrendingActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
    }
}
