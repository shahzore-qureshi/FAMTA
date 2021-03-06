package com.shahzorequreshi.famta.dagger

import com.shahzorequreshi.famta.activities.MainActivity
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.viewmodels.*
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component that defines which classes can inject Module singletons.
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(viewModel: SubwayServicesViewModel)
    fun inject(viewModel: SubwayBoundsViewModel)
    fun inject(viewModel: SubwayStationsViewModel)
    fun inject(viewModel: SubwayTimesViewModel)
    fun inject(viewModel: SubwayLinesViewModel)
    fun inject(repository: SubwayRepository)
    fun inject(activity: MainActivity)
}