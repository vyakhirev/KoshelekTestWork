package ru.vyakhirev.koshelektestwork.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.vyakhirev.koshelektestwork.presentation.details.DetailsViewModel
import ru.vyakhirev.koshelektestwork.presentation.info_ask.InfoAskViewModel
import ru.vyakhirev.koshelektestwork.presentation.info_bid.InfoBidViewModel
import javax.inject.Singleton

@Module
interface ViewModelModule {

    companion object {
        @Provides
        @Singleton
        fun viewModelsHolder(): @JvmSuppressWildcards HashMap<Class<out ViewModel>, ViewModel> {
            return HashMap()
        }

    }

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactoryProvider): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(InfoAskViewModel::class)
    fun bindsInfoAskViewModel(infoAskViewModel: InfoAskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoBidViewModel::class)
    fun bindsInfoBidViewModel(infoBidViewModel: InfoBidViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindsDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

}