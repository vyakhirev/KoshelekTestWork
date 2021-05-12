package ru.vyakhirev.koshelektestwork.presentation.info_bid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.vyakhirev.koshelektestwork.model.CurrencyModel
import ru.vyakhirev.koshelektestwork.model.DepthStreamModel
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.data.remote.WsBinance
import javax.inject.Inject

class InfoBidViewModel @Inject constructor(
    private val apiService: ApiBinance,
    private val wSocket: WsBinance
) : ViewModel() {

    var list: MutableList<CurrencyModel> = mutableListOf()

    var disposable = CompositeDisposable()

    private val _orders = MutableLiveData<MutableList<CurrencyModel>>()
    val orders: LiveData<MutableList<CurrencyModel>> = _orders


    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Boolean>()
    val onMessageError: LiveData<Boolean> = _onMessageError

    private val _wsStreamData = MutableLiveData<DepthStreamModel>()
    val wsStreamData: LiveData<DepthStreamModel> = _wsStreamData


    val gson = GsonBuilder().setLenient().create()


    fun getWsOrders(wsCur: String) {
        disposable.add(
            wSocket.onConnect(wsCur)
                .flatMapPublisher { open ->
                    open.client().listen()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isViewLoading.value = true
                        val gs = gson.fromJson(it.data(), DepthStreamModel::class.java)
                        _wsStreamData.value = gs
                        _isViewLoading.value = false
                    },
                    {
                        _onMessageError.value = true
                    }
                )
        )
    }

    fun getOrdersBook(symbol: String) {
        disposable.add(
            apiService.getOrdersBook(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isViewLoading.value = true
                        it.asks.map {
                            list.add(CurrencyModel(it[0], it[1]))
                        }
                        _orders.value = list
                        _isViewLoading.value = false
                    },
                    {
                        Log.d("Oshib", it.message.toString())
                    }
                )
        )
    }

    fun wsDisconnect() {
        wSocket.onDisconnect()
    }

    override fun onCleared() {
        disposable.clear()
        Log.d("OnCleared", "Invoked!")
        wsDisconnect()
    }
}