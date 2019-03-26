package io.chipotie.gifinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.chipotie.gifinder.utils.VIEW_MODEL_NOT_FOUND
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/*
 * @author savirdev on 25/03/19
 */

@Singleton
class ViewModelFactory
@Inject
constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("$VIEW_MODEL_NOT_FOUND - $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}