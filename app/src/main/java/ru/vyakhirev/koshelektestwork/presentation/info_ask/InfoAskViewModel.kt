package ru.vyakhirev.koshelektestwork.presentation.info_ask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.vyakhirev.koshelektestwork.data.model.CurrencyModel
import ru.vyakhirev.koshelektestwork.data.model.DepthStreamModel
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.data.remote.WsBinance
import javax.inject.Inject

class InfoAskViewModel @Inject constructor(
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
                        Log.d("kann", "Throwable=${it.message.toString()}")
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
                        _onMessageError.value = true
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


//    fun manageLocalOrderBook(apiCur: String, wsCur: String) {
//
//        getOrdersBook(apiCur)
//        var streamBuf: MutableList<DepthStreamModel> = mutableListOf()
//        var firstProcedEvent:DepthStreamModel?=null
//        disposable.add(
//            wSocket.onConnect(wsCur)
//                .flatMapPublisher {
//                    it.client().listen()
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map {
//                    gson.fromJson(it.data(), DepthStreamModel::class.java)
//                }
//                .filter{
//                    it.finalUpdate>lastUpdatedId
//                }
//                .subscribe({
//                    if (it.firstUpdate<=(lastUpdatedId+1)&&it.finalUpdate>=(lastUpdatedId+1)) {
//                        firstProcedEvent = it
//                        streamBuf.add(it)
//                        _wsStreamData.value=it
//                    }
//                    else if ((firstProcedEvent?.finalUpdate?.plus(1))==it.firstUpdate)
//                        _wsStreamData.value=it
//
//                    else
//                        _wsStreamData.value=it
//
//                },
//                    {
//                        Log.d("Error", "Error= ${it.message}")
//                    }
//                )
//        )
//    }
}