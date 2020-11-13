package ru.vyakhirev.koshelektestwork.presentation.info_ask

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vyakhirev.koshelektestwork.R

class InfoAskFragment : Fragment() {

    companion object {
        fun newInstance() = InfoAskFragment()
    }

    private lateinit var viewModel: InfoAskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_ask_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoAskViewModel::class.java)
        // TODO: Use the ViewModel
    }

}