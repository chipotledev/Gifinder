package io.chipotie.gifinder.ui.list

import android.view.View
import io.chipotie.gifinder.model.Gif

/*
 * @author savirdev on 24/03/19
 */

interface ListCallback{
    fun onRetry()
    fun onSelectedItem(data: Gif, view: View)
}