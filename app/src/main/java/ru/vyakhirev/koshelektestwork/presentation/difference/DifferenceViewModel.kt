package ru.vyakhirev.koshelektestwork.presentation.difference

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.vyakhirev.koshelektestwork.data.model.DepthStreamModel
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.data.remote.WsBinance
import javax.inject.Inject

class DifferenceViewModel @Inject constructor(
    private val apiService: ApiBinance,
    private val wSocket: WsBinance
) : ViewModel() {

    var disposable = CompositeDisposable()

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

    fun wsDisconnect() {
        wSocket.onDisconnect()
    }

    override fun onCleared() {
        disposable.clear()
        Log.d("OnCleared", "Invoked!")
        wsDisconnect()
    }
}