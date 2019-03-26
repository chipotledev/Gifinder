package io.chipotie.gifinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.chipotie.gifinder.datasource.GifDataSource
import io.chipotie.gifinder.datasource.GifDataSourceFactory
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.net.GifinderApi
import io.chipotie.gifinder.utils.Status
import javax.inject.Inject

/*
 * @author savirdev on 25/03/19
*/

class GifViewModel
@Inject
constructor(gifinderApi: GifinderApi): ViewModel(){
    private val gifDataSourceFactory : GifDataSourceFactory = GifDataSourceFactory(gifinderApi)
    var gifList : LiveData<PagedList<Gif>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(false)
            .build()

        gifList = LivePagedListBuilder<Long, Gif>(gifDataSourceFactory,config).build()
    }

    fun getState(): LiveData<Status> = Transformations.switchMap<GifDataSource,
            Status>(gifDataSourceFactory.gifDataSourceLiveData, GifDataSource::status)

    fun listIsEmpty(): Boolean {
        return gifList.value?.isEmpty() ?: true
    }

    fun retry(){
        gifDataSourceFactory.gifDataSourceLiveData.value?.retryAllFailed()
    }
}