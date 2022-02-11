package com.example.lesson7.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lesson7.R
import com.example.lesson7.databinding.FragmentHistoryBinding
import com.example.lesson7.utils.showSnackBar
import com.example.lesson7.view.recycler.HistoryAdapter
import com.example.lesson7.viewmodel.AppState
import com.example.lesson7.viewmodel.HistoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HistoryFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyFragmentRecyclerView.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.historyFragmentRecyclerView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                adapter.setData(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.historyFragmentRecyclerView.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                with(binding) {
                    historyFragmentRecyclerView.visibility = View.VISIBLE
                    includedLoadingLayout.loadingLayout.visibility = View.GONE
                    historyFragmentRecyclerView.showSnackBar(getString(R.string.error),
                        getString(R.string.reload),
                        {
                            viewModel.getAllHistory()
                        })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}