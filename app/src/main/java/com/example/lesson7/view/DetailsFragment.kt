package com.example.lesson7.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lesson7.R
import com.example.lesson7.databinding.FragmentDetailsBinding
import com.example.lesson7.model.FactDTO
import com.example.lesson7.model.Weather
import com.example.lesson7.model.WeatherDTO
import com.example.lesson7.model.getDefaultCity
import com.example.lesson7.utils.showSnackBar
import com.example.lesson7.viewmodel.AppState
import com.example.lesson7.viewmodel.DetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

private const val MAIN_LINK = "https://api.weather.yandex.ru/v2/forecast?"

class DetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.cityView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.cityView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.cityView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.cityView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromRemoteSource(
                            weatherBundle.city.lat,
                            weatherBundle.city.lon
                        )
                    })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        binding.itemViewCityName.text = city.city
        binding.itemViewCityTemp.text = weather.temperature.toString()
        binding.itemViewCityFeelsLike.text =
            getString(R.string.feelsLike) + weather.feelsLike.toString()
        binding.itemViewCityCondition.text = weather.condition
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}