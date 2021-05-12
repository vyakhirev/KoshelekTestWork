package ru.vyakhirev.koshelektestwork.di.viewmodel

import dagger.Component
import ru.vyakhirev.koshelektestwork.di.api.ApiModule
import ru.vyakhirev.koshelektestwork.presentation.info_ask.InfoAskFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class
    ]
)
interface ViewModelComponent : ViewModelsProvider {
    fun inject(askFragment: InfoAskFragment)
}