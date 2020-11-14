package ru.vyakhirev.koshelektestwork.presentation.info_ask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.vyakhirev.koshelektestwork.data.CurrencyModel
import ru.vyakhirev.koshelektestwork.data.model.AskModel
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.data.remote.WsBinance
import javax.inject.Inject

class InfoAskViewModel @Inject constructor(
    private val apiService:ApiBinance
) : ViewModel() {

    private var wSocket=WsBinance()

    var disposable = CompositeDisposable()

    private val _orders = MutableLiveData<List<CurrencyModel>>()
    val orders: LiveData<List<CurrencyModel>> = _orders


    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Boolean>()
    val onMessageError: LiveData<Boolean> = _onMessageError

    fun getWsOrders(){
        disposable.addAll(
        wSocket.onConnect()
        .flatMapPublisher { open ->
            open.client().listen()
        }
        .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                  Log.d("kann",it.data().toString())
                },
                {

                }
            )

        )
    }

    fun getOrdersBook(symbol:String){
        var list:MutableList<CurrencyModel> = mutableListOf()
        disposable.add(
        apiService.getOrdersBook(symbol)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isViewLoading.value = true
                    it.asks.map {
                       list.add(CurrencyModel(it[0],it[1]))
                    }
                    _orders.value =list
                    _isViewLoading.value = false
                },
                {
                    Log.d("Oshib",it.message.toString())
                }
            )
        )
    }

    override fun onCleared() {
        disposable.clear()
    }
}