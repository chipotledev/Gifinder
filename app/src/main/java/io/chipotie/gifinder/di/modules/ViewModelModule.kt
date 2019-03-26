package io.chipotie.gifinder.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.chipotie.gifinder.di.annotations.ViewModelKey
import io.chipotie.gifinder.viewmodel.GifViewModel
import io.chipotie.gifinder.viewmodel.SearchViewModel
import io.chipotie.gifinder.viewmodel.ViewModelFactory

/*
 * @author savirdev on 25/03/19
 */

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(GifViewModel::class)
    abstract fun bindGifViewModel(model: GifViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(model: SearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(sampleViewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}