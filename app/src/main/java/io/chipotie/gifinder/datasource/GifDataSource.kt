package io.chipotie.gifinder.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.chipotie.gifinder.BuildConfig
import io.chipotie.gifinder.model.Gif
import io.chipotie.gifinder.model.GifResponse
import io.chipotie.gifinder.net.GifinderApi
import io.chipotie.gifinder.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

/*
 * @author savirdev on 25/03/19
 */

class GifDataSource
@Inject
constructor(private val gifinderApi: GifinderApi, private val retryExecutor: Executor) :
    PageKeyedDataSource<Long, Gif>() {

    var status: MutableLiveData<Status> = MutableLiveData()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Gif>
    ) {

        updateState(Status.LOADING)

        gifinderApi.trending(BuildConfig.API_KEY, params.requestedLoadSize, 0).enqueue(object :
            Callback<GifResponse> {
            override fun onFailure(call: Call<GifResponse>, t: Throwable) {

                retry = {
                    loadInitial(params, callback)
                }
                updateState(Status.ERROR)
            }

            override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                retry = null
                updateState(Status.SUCCESS)
                response.body()?.data?.let { callback.onResult(it, null, 1) }
            }

        })

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Gif>) {

        updateState(Status.LOADING)

        gifinderApi.trending(BuildConfig.API_KEY, params.requestedLoadSize, params.requestedLoadSize * params.key).enqueue(object :
            Callback<GifResponse> {
            override fun onFailure(call: Call<GifResponse>, t: Throwable) {
                retry = {
                    loadAfter(params, callback)
                }
                updateState(Status.ERROR)
            }

            override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                retry = null
                updateState(Status.SUCCESS)
                response.body()?.data?.let { callback.onResult(it, params.key + 1) }
            }

        })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Gif>) {

    }

    private fun updateState(state: Status) {
        this.status.postValue(state)
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

}