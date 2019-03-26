package io.chipotie.gifinder.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.net.GifinderApi
import java.util.concurrent.Executors

/*
 * @author savirdev on 25/03/19
 */

class GifDataSourceFactory(val gifinderApi: GifinderApi): DataSource.Factory<Long, Gif>() {

    val gifDataSourceLiveData = MutableLiveData<GifDataSource>()

    override fun create(): DataSource<Long, Gif> {
        val gifDataSource =
            GifDataSource(gifinderApi, Executors.newFixedThreadPool(5))
        gifDataSourceLiveData.postValue(gifDataSource)
        return gifDataSource
    }

}