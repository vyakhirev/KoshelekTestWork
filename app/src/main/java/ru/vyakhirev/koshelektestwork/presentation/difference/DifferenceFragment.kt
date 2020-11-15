package ru.vyakhirev.koshelektestwork.presentation.difference

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
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.data.Currency
import ru.vyakhirev.koshelektestwork.data.DiffModel
import ru.vyakhirev.koshelektestwork.di.DaggerAppComponent
import ru.vyakhirev.koshelektestwork.presentation.difference.adapter.DifferenceAdapter
import javax.inject.Inject

class DifferenceFragment : Fragment() {

    companion object {
        fun newInstance() = DifferenceFragment()
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapterRv: DifferenceAdapter
    private lateinit var viewModel: DifferenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.difference_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerAppComponent.create().inject(this)

        setupCurrencySpinner()

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(DifferenceViewModel::class.java)

        setupRecyclerView()

        viewModel.wsDisconnect()
        viewModel.getWsOrders(Currency.wsBtcUsdt)

        viewModel.wsStreamData.observe(
            viewLifecycleOwner,
            {

                var diffModel:DiffModel=DiffModel(0.0,0.0,0.0,0.0,0.0,0.0)
                diffModel.bidPricePrevios=it.bids[0][0]
                diffModel.bidPriceNow=it.bids[1][0]
                diffModel.diffBid=diffModel.bidPriceNow-diffModel.bidPricePrevios
                diffModel.askPricePrevios=it.asks[0][1]
                diffModel.askPriceNow=it.asks[1][0]
                diffModel.diffAsk=diffModel.askPriceNow-diffModel.askPricePrevios
//                it.bids {
//                    if (it[1] != 0.0)
//                        diffModel.bidPricePrevios=it[0]
//                    if (it[2] != 0.0)
//                        diffModel.bidPriceNow=it[2]
//                }
//                it.asks.map {
//                    if (it[1] != 0.0)
//                        diffModel.askPricePrevios=it[0]
//                    if (it[2] != 0.0)
//                        diffModel.askPriceNow=it[2]
//                }
                adapterRv.addItem(diffModel)
                Log.d("dia", "wsStreamData=${diffModel.toString()}")
            }
        )

//        viewModel.orders.observe(
//            viewLifecycleOwner,
//            {
//                adapterRv.update(it)
//            })

        viewModel.isViewLoading.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    difUsers_loading_PB.visibility=View.VISIBLE
                }
                else
                    difUsers_loading_PB.visibility=View.GONE
            }
        )
    }

    private fun setupCurrencySpinner() {
        diffCurrencyChoserSpinner.setSelection(0)
        diffCurrencyChoserSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    when (parent.getItemAtPosition(position).toString()) {
                        Currency.btcUsdt -> {
                        }
                        Currency.bnbBtc -> {
                        }
                        Currency.ethBtc -> {
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
            DifferenceAdapter(
                requireContext(),
                mutableListOf()
            )
        currencyAskBidRV.layoutManager = LinearLayoutManager(context)
        currencyAskBidRV.adapter = adapterRv
    }
}