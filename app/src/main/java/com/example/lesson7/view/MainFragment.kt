package com.example.lesson7.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lesson7.R
import com.example.lesson7.databinding.FragmentMainBinding
import com.example.lesson7.model.Weather
import com.example.lesson7.utils.showSnackBar
import com.example.lesson7.view.recycler.CitiesRecyclerAdapter
import com.example.lesson7.viewmodel.AppState
import com.example.lesson7.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA_MENU = "menu"

        @JvmStatic
        val bundle = Bundle()
        fun newInstance(bundle: Bundle): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var isDataSetRus: Boolean = true

    private val adapter = CitiesRecyclerAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                manager.beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    //.addToBackStack("")
                    .commit()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromLocalSourceRus()

        isDataSetRus = when (arguments?.getString(BUNDLE_EXTRA_MENU)) {
            "isRussian" -> {
                viewModel.getWeatherFromLocalSourceRus()
                true
            }
            "isWorld" -> {
                viewModel.getWeatherFromLocalSourceWorld()
                false
            }
            else -> true
        }

        binding.recyclerView.adapter = adapter


        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
            viewModel.getWeatherFromLocalSourceRus()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeatherFromLocalSourceRus() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface OnItemViewClickListener {
    fun onItemViewClick(weather: Weather)
}