package ru.vyakhirev.koshelektestwork.di

import dagger.Component
import ru.vyakhirev.koshelektestwork.App
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.data.remote.WsBinance
import ru.vyakhirev.koshelektestwork.di.WebSocket.WsBinanceModule
import ru.vyakhirev.koshelektestwork.di.api.ApiModule
import ru.vyakhirev.koshelektestwork.di.viewmodel.ViewModelModule
import ru.vyakhirev.koshelektestwork.presentation.difference.DifferenceFragment
import ru.vyakhirev.koshelektestwork.presentation.info_ask.InfoAskFragment
import ru.vyakhirev.koshelektestwork.presentation.info_bid.InfoBidFragment
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        WsBinanceModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    companion object {

        fun create(): AppComponent {
            return DaggerAppComponent.create()
        }
    }

    fun provideApiBinance(): ApiBinance

    fun provideWsBinance(): WsBinance

    fun inject(app: App)

    fun inject(infoAskFragment: InfoAskFragment)

    fun inject(infoBidFragment: InfoBidFragment)

    fun inject(differenceFragment: DifferenceFragment)
}