package io.chipotie.gifinder.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import io.chipotie.gifinder.di.annotations.PerActivity
import io.chipotie.gifinder.di.modules.ActivityModule
import io.chipotie.gifinder.di.modules.RetrofitModule
import io.chipotie.gifinder.di.modules.ViewModelModule
import javax.inject.Singleton

/*
 * @author savirdev on 25/03/19
 */

@PerActivity
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityModule::class,  RetrofitModule::class, ViewModelModule::class])

interface AppComponent: AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance

        fun application(application: Application): Builder

        fun build() : AppComponent
    }
}