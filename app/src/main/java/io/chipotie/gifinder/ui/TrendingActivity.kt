package io.chipotie.gifinder.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import io.chipotie.gifinder.R
import io.chipotie.gifinder.databinding.ActivityTrendingBinding
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.utils.Status
import io.chipotie.gifinder.viewmodel.GifViewModel
import javax.inject.Inject

class TrendingActivity : DaggerAppCompatActivity(), ListCallback {

    private lateinit var binding : ActivityTrendingBinding

    private lateinit var gifListAdapter: GifListAdapter

    @Inject
    lateinit var gifViewModel: GifViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_trending)
        subscribeState()
        initLoadingData()
    }

    private fun initLoadingData() {
        gifListAdapter = GifListAdapter(this, this)

        gifViewModel.gifList.observe(this, Observer {
            gifListAdapter.submitList(it)
            Toast.makeText(this, "Yepa", Toast.LENGTH_SHORT).show()
        })

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL)
        val decorator = SpaceItemDecoration(8)
        binding.recyclerView.addItemDecoration(decorator)
        binding.recyclerView.adapter = gifListAdapter
    }

    private fun subscribeState() {

        gifViewModel.getState().observe(this, Observer { state ->
            binding.progressBar.visibility =
                if (gifViewModel.listIsEmpty() && state == Status.LOADING) View.VISIBLE else View.GONE
            binding.buttonError.visibility =
                if (gifViewModel.listIsEmpty() && state == Status.ERROR) View.VISIBLE else View.GONE
            if (!gifViewModel.listIsEmpty()) {
                gifListAdapter.setStatus(state ?: Status.SUCCESS)
            }
        })
    }

    fun retryLoad(){

    }

    override fun onRetry() {
        gifViewModel.retry()
    }

    override fun onSelectedItem(data: Gif) {

    }
}
