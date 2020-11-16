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
import kotlinx.android.synthetic.main.difference_fragment.*
import kotlinx.android.synthetic.main.info_ask_fragment.*
import kotlinx.android.synthetic.main.info_ask_fragment.diffRV
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.data.Currency
import ru.vyakhirev.koshelektestwork.data.model.CurrencyModel
import ru.vyakhirev.koshelektestwork.di.DaggerAppComponent
import ru.vyakhirev.koshelektestwork.presentation.base.adapter.CurrencyAdapter
import javax.inject.Inject


class InfoAskFragment : Fragment() {
    companion object {
        fun newInstance() = InfoAskFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: InfoAskViewModel
    private lateinit var adapterRv: CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_ask_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerAppComponent.create().inject(this)

        setupCurrencySpinner()

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(InfoAskViewModel::class.java)

        setupRecyclerView()

        viewModel.wsStreamData.observe(
            viewLifecycleOwner,
            {
                Log.d("dia", "wsStreamData=${it.toString()}")
                it.asks.map {

                    val currencyModel: CurrencyModel

                    if ((it[1] != 0.0)) {
                        currencyModel = CurrencyModel(it[0], it[1])
                        adapterRv.addItem(currencyModel)
                    }
                }
            }
        )

        viewModel.orders.observe(
            viewLifecycleOwner,
            {
                adapterRv.update(it)
            })

        viewModel.isViewLoading.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    askLoading_PB.visibility=View.VISIBLE
                }
                else
                    askLoading_PB.visibility=View.GONE
            }
        )

        viewModel.onMessageError.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    askLoading_PB.visibility=View.GONE
                    diffRV.visibility=View.GONE
                    askErrorImg.visibility=View.VISIBLE
                    askErrorTV.visibility=View.VISIBLE
                }
            }
        )

    }

    private fun setupCurrencySpinner() {
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
                        amountHeaderTV.text = getString(R.string.amount_btc)
                        priceHeaderTV.text = getString(R.string.amount_usdt)
                        adapterRv.clear()
                        viewModel.wsDisconnect()
                        viewModel.getWsOrders(Currency.wsBtcUsdt)
                    }
                    Currency.bnbBtc -> {
                        amountHeaderTV.text = getString(R.string.amountBNB)
                        priceHeaderTV.text = getString(R.string.priceBTC)
                        viewModel.orders.value?.clear()
                        viewModel.wsDisconnect()
                        viewModel.getWsOrders(Currency.wsBnbBtc)
                    }
                    Currency.ethBtc -> {
                        amountHeaderTV.text = getString(R.string.AmountEtn)
                        priceHeaderTV.text = getString(R.string.PriceBtc)
                        adapterRv.clear()
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
                isAsk = true
            )
        diffRV.layoutManager = LinearLayoutManager(context)
        diffRV.adapter = adapterRv
    }
}