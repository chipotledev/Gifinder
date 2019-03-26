package io.chipotie.gifinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.chipotie.gifinder.datasource.GifDataSource
import io.chipotie.gifinder.datasource.GifDataSourceFactory
import io.chipotie.gifinder.datasource.SearchDataSource
import io.chipotie.gifinder.datasource.SearchDataSourceFactory
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.net.GifinderApi
import io.chipotie.gifinder.utils.Status
import javax.inject.Inject

/*
 * @author savirdev on 25/03/19
*/

class SearchViewModel
@Inject
constructor(val gifinderApi: GifinderApi): ViewModel(){

    lateinit var gifList : LiveData<PagedList<Gif>>
    lateinit var searchDataSourceFactory: SearchDataSourceFactory

    fun initLoad(query:String){
        searchDataSourceFactory = SearchDataSourceFactory(gifinderApi, query)

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(false)
            .build()

        gifList = LivePagedListBuilder<Long, Gif>(searchDataSourceFactory,config).build()
    }

    fun getState(): LiveData<Status> = Transformations.switchMap<SearchDataSource,
            Status>(searchDataSourceFactory.searchDataSourceLiveData, SearchDataSource::status)

    fun listIsEmpty(): Boolean {
        return gifList.value?.isEmpty() ?: true
    }

    fun retry(){
        searchDataSourceFactory.searchDataSourceLiveData.value?.retryAllFailed()
    }
}