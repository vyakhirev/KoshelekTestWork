package ru.vyakhirev.koshelektestwork.di

import dagger.Component
import ru.vyakhirev.koshelektestwork.App
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.di.api.ApiModule
import ru.vyakhirev.koshelektestwork.di.viewmodel.ViewModelModule
import ru.vyakhirev.koshelektestwork.presentation.info_ask.InfoAskFragment
import javax.inject.Singleton

@Component(modules = [
    ApiModule::class,
    ViewModelModule::class
])
@Singleton
interface AppComponent {

    companion object {

        fun create(): AppComponent {
            return DaggerAppComponent.create()
        }
    }

    fun provideOwmApi(): ApiBinance

    fun inject(app: App)

    fun inject(infoAskFragment: InfoAskFragment)
}