package io.chipotie.gifinder.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.utils.Status

/*
 * @author savirdev on 25/03/19
 */

class GifListAdapter(private val callback: ListCallback, val context: Context) : PagedListAdapter<Gif, RecyclerView.ViewHolder>(GifDiffCallback){

    private var status = Status.LOADING

    private val GIF_TYPE= 1
    private val FOOTER_VIEW_TYPE = 2

    companion object {

        val GifDiffCallback = object : DiffUtil.ItemCallback<Gif>(){
            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GIF_TYPE){
            GifItemViewHolder.create(callback,parent, context)
        }
        else {
            ListFooterViewHolder.create(callback, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (GIF_TYPE) {
            getItemViewType(position) -> (holder as GifItemViewHolder).bind(getItem(position))
            else -> (holder as ListFooterViewHolder).bind(status)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()){
            GIF_TYPE
        } else{
            FOOTER_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean{
        return super.getItemCount() != 0 && (status == Status.LOADING) || (status == Status.ERROR)
    }

    fun setStatus(state: Status) {
        this.status = state
        notifyItemChanged(super.getItemCount())
    }

    interface Callback{
        fun onRetry()
        fun onSelectedItem(manufacturer: Gif)
    }
}