package io.chipotie.gifinder.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.chipotie.gifinder.R
import io.chipotie.gifinder.utils.Status
import kotlinx.android.synthetic.main.item_list_footer.view.*

/*
 * @author savirdev on 22/03/19
 */

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: Status?) {
        itemView.progress_bar.visibility = if (status == Status.LOADING) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == Status.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(callback: ListCallback, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.txt_error.setOnClickListener { callback.onRetry() }
            return ListFooterViewHolder(view)
        }
    }
}