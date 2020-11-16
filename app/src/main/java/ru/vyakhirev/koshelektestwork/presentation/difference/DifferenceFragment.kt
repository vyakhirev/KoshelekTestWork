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
import ru.vyakhirev.koshelektestwork.data.model.DiffModel
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

                val diffModel = DiffModel(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
                diffModel.bidPricePrevios = it.bids[0][0]
                diffModel.bidPriceNow = it.bids[1][0]
                diffModel.diffBid = diffModel.bidPriceNow - diffModel.bidPricePrevios
                diffModel.askPricePrevios = it.asks[0][0]
                diffModel.askPriceNow = it.asks[1][0]
                diffModel.diffAsk = diffModel.askPriceNow - diffModel.askPricePrevios
                adapterRv.addItem(diffModel)
                Log.d("dia", "wsStreamData=${diffModel.toString()}")
            }
        )

        viewModel.isViewLoading.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    difUsers_loading_PB.visibility = View.VISIBLE
                } else
                    difUsers_loading_PB.visibility = View.GONE
            }
        )

        viewModel.onMessageError.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    difUsers_loading_PB.visibility = View.GONE
                    diffRV.visibility = View.GONE
                    diffErrorImg.visibility = View.VISIBLE
                    diffErrorTV.visibility = View.VISIBLE
                }
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
                            adapterRv.clear()
                            viewModel.wsDisconnect()
                            viewModel.getWsOrders(Currency.wsBtcUsdt)
                        }
                        Currency.bnbBtc -> {
                            adapterRv.clear()
                            viewModel.wsDisconnect()
                            viewModel.getWsOrders(Currency.wsBnbBtc)
                        }
                        Currency.ethBtc -> {
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
            DifferenceAdapter(
                requireContext(),
                mutableListOf()
            )
        diffRV.layoutManager = LinearLayoutManager(context)
        diffRV.adapter = adapterRv
    }
}