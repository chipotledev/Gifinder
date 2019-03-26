package io.chipotie.gifinder.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.chipotie.gifinder.ui.SearchActivity
import io.chipotie.gifinder.ui.TrendingActivity

/*
 * @author savirdev on 25/03/19
 */

@Module
abstract class ActivityModule{
    @ContributesAndroidInjector
    abstract fun contributeTrendingActivity(): TrendingActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity
}