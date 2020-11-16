package ru.vyakhirev.koshelektestwork.presentation.info_bid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.info_bid_fragment.*
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.data.Currency
import ru.vyakhirev.koshelektestwork.data.model.CurrencyModel
import ru.vyakhirev.koshelektestwork.di.DaggerAppComponent
import ru.vyakhirev.koshelektestwork.presentation.base.adapter.CurrencyAdapter
import javax.inject.Inject

class InfoBidFragment : Fragment() {

    companion object {
        fun newInstance() = InfoBidFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: InfoBidViewModel
    private lateinit var adapterRv: CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_bid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerAppComponent.create().inject(this)

        setupCurrencySpinner()

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(InfoBidViewModel::class.java)

        setupRecyclerView()

        viewModel.wsStreamData.observe(
            viewLifecycleOwner,
            {
                Log.d("dia", "wsStreamData=${it.toString()}")
                it.bids.map {
                    var currencyModel: CurrencyModel

                    if ((it[1] != 0.0)) {
                        currencyModel = CurrencyModel(it[0], it[1])
                        adapterRv.addItem(currencyModel)
                    }
                }
            }
        )

        viewModel.isViewLoading.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    bidUsers_loading_PB.visibility = View.VISIBLE
                } else
                    bidUsers_loading_PB.visibility = View.GONE
            }
        )

        viewModel.onMessageError.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    bidUsers_loading_PB.visibility = View.GONE
                    currencyBidRV.visibility = View.GONE
                    bidErrorImg.visibility = View.VISIBLE
                    bidErrorTV.visibility = View.VISIBLE
                }
            }
        )

    }

    private fun setupCurrencySpinner() {
        bidCurrencyChoserSpinner.setSelection(0)
        bidCurrencyChoserSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    when (parent.getItemAtPosition(position).toString()) {
                        Currency.btcUsdt -> {
                            bidAmountHeaderTV.text = "Amount BTC"
                            bidPriceHeaderTV.text = "Price USDT"
                            viewModel.wsDisconnect()
                            viewModel.getWsOrders(Currency.wsBtcUsdt)
                        }
                        Currency.bnbBtc -> {
                            bidAmountHeaderTV.text = "Amount BNB"
                            bidPriceHeaderTV.text = "Price BTC"
                            viewModel.wsDisconnect()
                            viewModel.getWsOrders(Currency.wsBnbBtc)
                        }
                        Currency.ethBtc -> {
                            bidAmountHeaderTV.text = "Amount ETH"
                            bidPriceHeaderTV.text = "Price BTC"
                            viewModel.wsDisconnect()
                            viewModel.getWsOrders(Currency.wsEthBtc)
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
                mutableListOf(),
                isAsk = false
            )
        currencyBidRV.layoutManager = LinearLayoutManager(context)
        currencyBidRV.adapter = adapterRv
    }
}