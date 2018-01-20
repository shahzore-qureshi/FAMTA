package com.shahzorequreshi.famta.dagger

import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.viewmodels.SubwayViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component that defines which classes can inject Module singletons.
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(viewModel: SubwayViewModel)
    fun inject(repository: SubwayRepository)
}