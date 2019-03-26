package io.chipotie.gifinder.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import io.chipotie.gifinder.R
import io.chipotie.gifinder.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)

        retrieveQuery()
        binding.tvStatus.text = getString(R.string.found_results, query)
    }

    private fun retrieveQuery(){
        query = intent?.extras?.getString(TrendingActivity.EXTRA_QUERY)
    }

    /*private fun setupSearchView(){
        binding.svSearch.setIconifiedByDefault(true)
        binding.svSearch.setQuery(query,true)
        binding.svSearch.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@SearchActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }
        })
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView : SearchView = searchItem?.actionView as SearchView
        searchItem.expandActionView()
        searchView.setQuery(query,false)
        searchView.clearFocus()

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@SearchActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }
        })
        return true
    }
}
