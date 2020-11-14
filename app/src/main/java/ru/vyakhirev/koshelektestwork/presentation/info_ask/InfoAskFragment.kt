package ru.vyakhirev.koshelektestwork.presentation.info_ask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.navin.flintstones.rxwebsocket.RxWebsocket
import com.navin.flintstones.rxwebsocket.RxWebsocket.Open
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions.emptyConsumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.info_ask_fragment.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.data.Currency
import ru.vyakhirev.koshelektestwork.data.model.DepthStreamModel
import ru.vyakhirev.koshelektestwork.data.remote.ApiBinance
import ru.vyakhirev.koshelektestwork.data.remote.WebSocketConverterFactory
import ru.vyakhirev.koshelektestwork.di.DaggerAppComponent
import ru.vyakhirev.koshelektestwork.presentation.info_ask.adapter.CurrencyAdapter
import javax.inject.Inject

class InfoAskFragment : Fragment() {
    companion object {
        fun newInstance() = InfoAskFragment()
    }

    @Inject
    lateinit var apiService:ApiBinance

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory



    private lateinit var viewModel: InfoAskViewModel
    private lateinit var adapterRv:CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_ask_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerAppComponent.create().inject(this)

        setupCurrencySpoinner()

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(InfoAskViewModel::class.java)

        setupRecyclerView()

         viewModel.getOrdersBook("BTCUSDT")

        viewModel.getWsOrders()


        viewModel.orders.observe(
            viewLifecycleOwner,
            {
                Log.d("lld", it.toString())
                adapterRv.update(it)
//                Thread.sleep(3000)
//                viewModel.getOrdersBook("BTCUSDT")
            })


    }





    private fun setupCurrencySpoinner() {
        currencyChoserSpinner.setSelection(0)
        currencyChoserSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (parent.getItemAtPosition(position).toString()) {
                    Currency.btcUsdt -> {
                        amountHeaderTV.text = "Amount BTC"
                        priceHeaderTV.text = "Price USDT"
                    }
                    Currency.bnbBtc -> {
                        amountHeaderTV.text = "Amount BNB"
                        priceHeaderTV.text = "Price BTC"
                    }
                    Currency.ethBtc -> {
                        amountHeaderTV.text = "Amount ETH"
                        priceHeaderTV.text = "Price BTC"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    private fun setupRecyclerView() {

        adapterRv =
            CurrencyAdapter(
                requireContext(),
                listOf()
            )
        currencyAskBidRV.layoutManager = LinearLayoutManager(context)
        currencyAskBidRV.adapter = adapterRv
    }
}