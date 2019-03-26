package io.chipotie.gifinder.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.net.GifinderApi
import java.util.concurrent.Executors

/*
 * @author savirdev on 25/03/19
 */

class SearchDataSourceFactory(val gifinderApi: GifinderApi, val query: String): DataSource.Factory<Long, Gif>() {

    val searchDataSourceLiveData = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Long, Gif> {
        val searchDataSource =
            SearchDataSource(gifinderApi, query, Executors.newFixedThreadPool(5))
        searchDataSourceLiveData.postValue(searchDataSource)
        return searchDataSource
    }

}