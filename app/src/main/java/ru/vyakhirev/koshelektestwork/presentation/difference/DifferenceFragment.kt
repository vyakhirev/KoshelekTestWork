package ru.vyakhirev.koshelektestwork.presentation.difference

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.difference_fragment.*
import kotlinx.android.synthetic.main.info_ask_fragment.*
import kotlinx.android.synthetic.main.info_ask_fragment.currencyChoserSpinner
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.data.Currency
import ru.vyakhirev.koshelektestwork.presentation.base.adapter.CurrencyAdapter

class DifferenceFragment : Fragment() {

    companion object {
        fun newInstance() = DifferenceFragment()
    }

    private lateinit var viewModel: DifferenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.difference_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DifferenceViewModel::class.java)
        // TODO: Use the ViewModel
       setupCurrencySpoinner()
    }

    private fun setupCurrencySpoinner() {
        diffCurrencyChoserSpinner.setSelection(0)
        diffCurrencyChoserSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

//    private fun setupRecyclerView() {
//
//        adapterRv =
//            CurrencyAdapter(
//                requireContext(),
//                mutableListOf(),
//                isAsk = true
//            )
//        currencyAskBidRV.layoutManager = LinearLayoutManager(context)
//        currencyAskBidRV.adapter = adapterRv
//    }
}