package com.gangwonhyuil.gangwonhyuil.ui.home.office

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.gangwonhyuil.gangwonhyuil.data.remote.office.OfficeDataSource
import com.gangwonhyuil.gangwonhyuil.data.remote.tour.TourDataSource
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val TOUR_API_KEY: String = BuildConfig.TOUR_API_KEY

const val EXTRA_PLACE_ID = "extra_place_id"

@HiltViewModel
class PlaceViewModel
@Inject
constructor(
    private val tourClient: TourDataSource,
    private val officeClient: OfficeDataSource
) : BaseViewModel() {
    private val _sigunguCode = MutableStateFlow<String>("")
    val sigunguCode: StateFlow<String> get() = _sigunguCode
    private val _officeLocation = MutableStateFlow<String>("eq.1")
    val officeLocation: StateFlow<String> get() = _officeLocation

    private val _itemList = MutableStateFlow<Map<String, List<PlaceItem>>>(emptyMap())
    val itemList: StateFlow<Map<String, List<PlaceItem>>> get() = _itemList

    init {
        viewModelScopeEH.launch {
            val categories = listOf("공유 오피스", "숙소", "식당", "카페")
            categories.forEach { category ->
                fetchAllPlaceCategories(category)
            }
            fetchOfficeRatingList()
        }
    }

    suspend fun fetchOfficeRatingList() {
        try {
            val response =
                officeClient.getOfficeRatingList(listOf("2745986", "2512985"))

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Timber.d("별점 response 성공: ${response.body()}")
                }
            } else {
                Timber.d("별점 response 실패 %s", response.errorBody()?.string())
            }
        } catch (e: Exception) {
            Timber.d("별점 response 에러 %s", e.message)
        }
    }

    internal fun updateSelectedLocation(location: String) {
        when (location) {
            "춘천" -> {
                _sigunguCode.value = "13"
                _officeLocation.value = "eq.0"
            }

            "강릉" -> {
                _sigunguCode.value = "1"
                _officeLocation.value = "eq.1"
            }

            "동해" -> {
                _sigunguCode.value = "3"
                _officeLocation.value = "eq.2"
            }

            "영월" -> {
                _sigunguCode.value = "8"
                _officeLocation.value = "eq.3"
            }

            "속초" -> {
                _sigunguCode.value = "5"
                _officeLocation.value = "eq.4"
            }

            "원주" -> {
                _sigunguCode.value = "9"
                _officeLocation.value = "eq.5"
            }
        }
    }

    internal fun fetchTourAPICategories() {
        viewModelScopeEH.launch {
            val categories = listOf("숙소", "식당", "카페")
            categories.forEach { category ->
                fetchAllPlaceCategories(category)
            }
        }
    }

    private suspend fun fetchAllPlaceCategories(category: String) {

        if (category == "공유 오피스") {
            getOfficeList()
            return
        }

        val params = when (category) {
            "숙소" -> CategoryParams("32", "B02", "B0201", "B02010100")
            "식당" -> CategoryParams("39", "A05", "A0502", "A05020100")
            "카페" -> CategoryParams("39", "A05", "A0502", "A05020900")
            else -> return
        }

        viewModelScopeEH.launch {
            try {
                val response =
                    tourClient.getAreaBasedList(
                        numOfRows = 10,
                        pageNo = 1,
                        contentTypeId = params.contentTypeId,
                        areaCode = "32",
                        sigunguCode = sigunguCode.value,
                        cat1 = params.cat1,
                        cat2 = params.cat2,
                        cat3 = params.cat3,
                        serviceKey = TOUR_API_KEY
                    )

                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        Timber.d("response 성공: ${response.body()}")
                        val items = responseBody.response.body.items.item ?: emptyList()
                        val placeItems = items.map { apiItem ->
                            PlaceItem(
                                id = apiItem.contentid.toLong(),
                                name = apiItem.title,
                                address = apiItem.addr1,
                                image = apiItem.firstimage,
                            )
                        }
                        _itemList.value =
                            _itemList.value.toMutableMap().apply { put(category, placeItems) }
                        Timber.d("_itemList: ${_itemList.value}")
                        Timber.d("response 성공: ${items}")
                    }
                } else {
                    Timber.d("response 실패 %s", response.errorBody()?.string())
                }
            } catch (e: Exception) {
                Timber.d("response 에러 %s", e.message)
            }
        }
    }

    suspend fun getOfficeList() {
        viewModelScopeEH.launch {
            try {
                val response =
                    officeClient.getOfficeList(
                        location = officeLocation.value
                    )
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        Timber.d("office response 성공: ${responseBody}")
                        val placeItems = responseBody.flatMap { apiItem ->
                            apiItem.offices.map { office ->
                                PlaceItem(
                                    id = office.idx.toLong(),
                                    name = office.name,
                                    address = office.address,
                                    image = office.imageUrl ?: "",
                                )
                            }
                        }

                        _itemList.value =
                            _itemList.value.toMutableMap().apply { put("공유 오피스", placeItems) }
                    }
                } else {
                    Timber.d("office response 실패 %s", response.errorBody()?.string())
                }

            } catch (e: Exception) {
                Timber.d("office response 에러 %s", e.message)
            }
        }
    }
}