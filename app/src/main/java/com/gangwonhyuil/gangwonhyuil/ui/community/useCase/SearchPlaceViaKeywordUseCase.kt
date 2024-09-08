package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLocal.KakaoLocalDataSource
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.data.response.kakaoLocal.SearchViaKeywordResponse
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace.SearchResultItem
import timber.log.Timber
import javax.inject.Inject

class SearchPlaceViaKeywordUseCase
    @Inject
    constructor(
        private val kakaoLocalDataSource: KakaoLocalDataSource,
    ) {
        suspend operator fun invoke(keyword: String): List<SearchResultItem> =
            when (val customResponse = searchViaKeyword(keyword)) {
                is CustomResponse.Success<*> -> {
                    (customResponse.data as SearchViaKeywordResponse).documents.map {
                        SearchResultItem(
                            id = it.id,
                            categoryGroup = it.categoryName,
                            name = it.placeName,
                            roadAddress = it.roadAddressName,
                            url = it.placeUrl
                        )
                    }
                }

                is CustomResponse.Failure -> {
                    Timber.e(customResponse.e)
                    emptyList()
                }
            }

        private suspend fun searchViaKeyword(query: String): CustomResponse =
            try {
                val response = kakaoLocalDataSource.searchViaKeyword(query)
                CustomResponse.Success(response)
            } catch (e: Exception) {
                CustomResponse.Failure(e)
            }
    }