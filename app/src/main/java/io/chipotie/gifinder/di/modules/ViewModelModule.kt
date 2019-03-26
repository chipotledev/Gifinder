package io.chipotie.gifinder.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import io.chipotie.gifinder.viewmodel.ViewModelFactory

/*
 * @author savirdev on 25/03/19
 */

@Module
abstract class ViewModelModule{

    @Binds
    abstract fun bindViewModelFactory(sampleViewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}