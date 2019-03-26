package io.chipotie.gifinder.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import io.chipotie.gifinder.R
import io.chipotie.gifinder.databinding.ActivitySearchBinding
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.ui.list.GifListAdapter
import io.chipotie.gifinder.ui.list.ListCallback
import io.chipotie.gifinder.ui.list.SpaceItemDecoration
import io.chipotie.gifinder.utils.Status
import io.chipotie.gifinder.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity(), ListCallback {

    private lateinit var binding: ActivitySearchBinding

    private var query: String? = null

    private var searchItem: MenuItem? = null

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private lateinit var gifListAdapter: GifListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        binding.view = this

        retrieveQuery()

        initLoadingData()
        subscribeState()
    }

    private fun retrieveQuery(){
        query = intent?.extras?.getString(TrendingActivity.EXTRA_QUERY)
    }

    private fun initLoadingData(){
        query?.let {
            searchViewModel.initLoad(it)
            gifListAdapter = GifListAdapter(this, this)

            searchViewModel.gifList.observe(this, Observer { list ->
                gifListAdapter.submitList(list)
            })

            binding.rvSearch.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            val decorator = SpaceItemDecoration(8)
            binding.rvSearch.addItemDecoration(decorator)
            binding.rvSearch.adapter = gifListAdapter
        }

    }

    private fun subscribeState() {

        searchViewModel.getState().observe(this, Observer { state ->
            binding.progressBar.visibility =
                if (searchViewModel.listIsEmpty() && state == Status.LOADING) View.VISIBLE else View.GONE
            binding.buttonError.visibility =
                if (searchViewModel.listIsEmpty() && state == Status.ERROR) View.VISIBLE else View.GONE
            if (!searchViewModel.listIsEmpty()) {
                binding.tvStatus.text = getString(R.string.found_results, query)
                gifListAdapter.setStatus(state ?: Status.SUCCESS)
            }else{
                binding.tvStatus.text = getString(R.string.no_results, query)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        searchItem = menu?.findItem(R.id.action_search)
        val searchView : SearchView = searchItem?.actionView as SearchView
        searchView.setQuery(query,false)
        searchView.clearFocus()

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.gifList.removeObservers(this@SearchActivity)
                    searchViewModel.initLoad(it)
                    subscribeState()
                    searchViewModel.gifList.observe(this@SearchActivity, Observer { list ->
                        this@SearchActivity.query = query
                        gifListAdapter.submitList(list)
                    })
                }
                return false
            }
        })
        return true
    }

    fun retryLoad(){
        searchViewModel.retry()
    }

    override fun onRetry() {
        searchViewModel.retry()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSelectedItem(data: Gif, view: View) {
        val intent = Intent(this, ZoomActivity::class.java)
        intent.putExtra(TrendingActivity.EXTRA_GIF, data)

        val options = ActivityOptions
            .makeSceneTransitionAnimation(this, view, "gif_zoom")

        startActivity(intent, options.toBundle())
    }
}
