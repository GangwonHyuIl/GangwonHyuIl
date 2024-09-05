package com.gangwonhyuil.gangwonhyuil.ui.home

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.gangwonhyuil.gangwonhyuil.data.api.WeatherClient
import com.gangwonhyuil.gangwonhyuil.data.response.weather.Item
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _selectedLocationXY = MutableStateFlow<List<Int>>(emptyList())
    val selectedLocationXY: StateFlow<List<Int>> get() = _selectedLocationXY

    private val _weatherData = MutableStateFlow<Map<String, String>>(emptyMap())
    val weatherData: StateFlow<Map<String, String>> get() = _weatherData
    private val _baseTime = MutableStateFlow<String>("")
    val baseTime: StateFlow<String> get() = _baseTime
    private val _baseDate = MutableStateFlow<String>("")
    val baseDate: StateFlow<String> get() = _baseDate


    fun updateSelectedLocationXY(location: String) {
        when (location) {
            "강릉" -> _selectedLocationXY.value = listOf(92, 131)
            "속초" -> _selectedLocationXY.value = listOf(87, 141)
            "동해" -> _selectedLocationXY.value = listOf(97, 127)
            "춘천" -> _selectedLocationXY.value = listOf(73, 134)
        }
    }

    fun fetchWeatherConditions(xyList: List<Int>) {
        viewModelScopeEH.launch {
            try {
                getCurrentDateFormatted()
                getBaseTimeFormatted()
                val response = WeatherClient.weatherNetWork.getWeather(
                    serviceKey = WEATHER_API_KEY,
                    numOfRow = 12,
                    dataType = "JSON",
                    pageNo = 1,
                    baseDate = baseDate.value,
                    baseTime = baseTime.value,
                    nx = xyList.first(),
                    ny = xyList.last()
                )

                if (response.isSuccessful) {
                    response.body().let {
                        Timber.d("response 성공: ${response.body()}")
                        val items = response.body()?.response?.body?.items?.item ?: emptyList()
                        val filteredItems = filterWeatherItems(items)

                        _weatherData.value = filteredItems
                    }
                } else {
                    Timber.d("response 실패 %s", response.errorBody()?.string())
                }


            } catch (e: Exception) {
                Timber.d("response 에러 %s", e.message)
            }
        }
    }

    private fun getCurrentDateFormatted() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val newDate = today.format(formatter)

        if (_baseDate.value != newDate) {
            _baseDate.value = newDate
            Timber.d("Updated baseDate: ${_baseDate.value}")
        }
    }

    private fun getBaseTimeFormatted() {
        val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm")).toInt()
        val baseTimes = listOf(200, 500, 800, 1100, 1400, 1700, 2000, 2300)
        val newBaseTime = baseTimes.lastOrNull { it <= currentTime }?.toString()?.padStart(4, '0')
            ?: baseTimes.last().toString().padStart(4, '0')

        if (_baseTime.value != newBaseTime) {
            _baseTime.value = newBaseTime
            Timber.d("Updated baseTime: ${_baseTime.value}")
        }
    }

    private fun filterWeatherItems(items: List<Item>): Map<String, String> {
        return items.filter { it.category == "POP" || it.category == "PTY" }
            .associate { it.category to it.fcstValue }
    }
}