package io.chipotie.gifinder.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import io.chipotie.gifinder.R
import io.chipotie.gifinder.databinding.ActivityTrendingBinding
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.ui.list.GifListAdapter
import io.chipotie.gifinder.ui.list.ListCallback
import io.chipotie.gifinder.ui.list.SpaceItemDecoration
import io.chipotie.gifinder.utils.Status
import io.chipotie.gifinder.viewmodel.GifViewModel
import javax.inject.Inject

class TrendingActivity : DaggerAppCompatActivity(), ListCallback {

    companion object {
        val EXTRA_GIF = "EXTRA_GIF"
        val EXTRA_QUERY = "EXTRA_QUERY"
    }

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
        gifViewModel.retry()
    }

    override fun onRetry() {
        gifViewModel.retry()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSelectedItem(data: Gif, view: View) {
        val intent = Intent(this, ZoomActivity::class.java)
        intent.putExtra(EXTRA_GIF, data)

        val options = ActivityOptions
            .makeSceneTransitionAnimation(this, view, "gif_zoom")

        startActivity(intent, options.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_trending, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@TrendingActivity, query, Toast.LENGTH_SHORT).show()

                val intent = Intent(this@TrendingActivity, SearchActivity::class.java)
                intent.putExtra(EXTRA_QUERY, query)
                startActivity(intent)
                return false
            }
        })
        return true
    }
}
