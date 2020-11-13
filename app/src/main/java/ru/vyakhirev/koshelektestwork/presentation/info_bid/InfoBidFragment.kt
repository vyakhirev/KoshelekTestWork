package ru.vyakhirev.koshelektestwork.presentation.info_bid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vyakhirev.koshelektestwork.R

class InfoBidFragment : Fragment() {

    companion object {
        fun newInstance() = InfoBidFragment()
    }

    private lateinit var viewModel: InfoBidViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_bid_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoBidViewModel::class.java)
        // TODO: Use the ViewModel
    }

}