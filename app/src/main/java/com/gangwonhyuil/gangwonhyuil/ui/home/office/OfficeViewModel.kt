package com.gangwonhyuil.gangwonhyuil.ui.home.office

import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OfficeViewModel @Inject constructor() : BaseViewModel() {
    private val _officeList = MutableStateFlow<List<Office>>(emptyList())
    val officeList: StateFlow<List<Office>> get() = _officeList
    fun loadOfficeData(category: String) {
        val officeList = when (category) {
            "공유 오피스" -> listOf(
                Office.OfficeItem(
                    id = 1L,
                    name = "Office1",
                    address = "Address1",
                    image = "https://modo-phinf.pstatic.net/20210817_245/16291670132053Hwtc_JPEG/mosanF226Z.jpeg?type=f564_336",
                    rating = "1.1"
                ),
                Office.OfficeItem(
                    id = 2L,
                    name = "Office1",
                    address = "Address1",
                    image = "https://cdn.imweb.me/thumbnail/20201119/111278d6072ae.png",
                    rating = "1.2"
                ),
                Office.OfficeItem(
                    id = 3L,
                    name = "Office1",
                    address = "Address1",
                    image = "https://modo-phinf.pstatic.net/20210817_245/16291670132053Hwtc_JPEG/mosanF226Z.jpeg?type=f564_336",
                    rating = "1.3"
                ),
                Office.OfficeItem(
                    id = 4L,
                    name = "Office1",
                    address = "Address1",
                    image = "https://cdn.imweb.me/thumbnail/20201119/111278d6072ae.png",
                    rating = "1.4"
                ),
                Office.OfficeItem(
                    id = 5L,
                    name = "Office1",
                    address = "Address1",
                    image = "https://modo-phinf.pstatic.net/20210817_245/16291670132053Hwtc_JPEG/mosanF226Z.jpeg?type=f564_336",
                    rating = "1.5"
                ),
                Office.OfficeItem(
                    id = 6L,
                    name = "Office1",
                    address = "Address1",
                    image = "https://cdn.imweb.me/thumbnail/20201119/111278d6072ae.png",
                    rating = "1.6"
                )
            )

            "숙소" -> listOf(
                Office.OfficeItem(
                    id = 7L,
                    name = "Office2",
                    address = "Address2",
                    image = "https://cdn.travie.com/news/photo/202102/21745_10249_2656.jpg",
                    rating = "2.1"
                ),
                Office.OfficeItem(
                    id = 8L,
                    name = "Office2",
                    address = "Address2",
                    image = "https://cdn.travie.com/news/photo/202102/21745_10252_2756.jpg",
                    rating = "2.2"
                )
            )

            "식당" -> listOf(
                Office.OfficeItem(
                    id = 9L,
                    name = "Office3",
                    address = "Address3",
                    image = "https://www.kopo.ac.kr/ckimage/2022/51/DV6uJfveUpd4DT1bm8PU.png",
                    rating = "3.1"
                ),
                Office.OfficeItem(
                    id = 10L,
                    name = "Office3",
                    address = "Address3",
                    image = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1c/0b/7d/bd/inside.jpg?w=1100&h=-1&s=1",
                    rating = "3.2"
                )
            )

            "카페" -> listOf(
                Office.OfficeItem(
                    id = 11L,
                    name = "Office4",
                    address = "Address4",
                    image = "https://www.jeongdong.or.kr/static/portal/img/HKPU_04_04_pic1.jpg",
                    rating = "4.1"
                ),
                Office.OfficeItem(
                    id = 12L,
                    name = "Office4",
                    address = "Address4",
                    image = "https://cdn.telltrip.com/news/photo/202401/814_4253_5644.png",
                    rating = "4.2"
                )
            )

            else -> emptyList()
        }
        _officeList.value = officeList
    }

}