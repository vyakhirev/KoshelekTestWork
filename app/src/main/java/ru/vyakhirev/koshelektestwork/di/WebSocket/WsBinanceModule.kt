package ru.vyakhirev.koshelektestwork.di.WebSocket

import dagger.Module
import dagger.Provides
import ru.vyakhirev.koshelektestwork.data.remote.WsBinance
import javax.inject.Singleton

@Module
class WsBinanceModule {

    @Singleton
    @Provides
    fun provideWsBinance(): WsBinance {
        return WsBinance()
    }
}