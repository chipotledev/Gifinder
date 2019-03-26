package io.chipotie.gifinder

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.chipotie.gifinder.di.components.DaggerAppComponent

/*
 * @author savirdev on 25/03/19
 */

class GifinderApplication : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}