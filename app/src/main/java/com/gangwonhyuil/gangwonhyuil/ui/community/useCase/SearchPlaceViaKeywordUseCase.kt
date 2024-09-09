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
        suspend operator fun invoke(
            keyword: String,
            searchPage: Int,
        ): Pair<List<SearchResultItem>, Boolean> =
            when (val customResponse = searchViaKeyword(keyword, searchPage)) {
                is CustomResponse.Success<*> -> {
                    val resultItems =
                        (customResponse.data as SearchViaKeywordResponse).documents.map {
                            SearchResultItem(
                                id = it.id,
                                categoryGroup = it.categoryName,
                                name = it.placeName,
                                roadAddress = it.roadAddressName,
                                url = it.placeUrl
                            )
                        }
                    val isEnd = customResponse.data.meta.isEnd
                    Pair(resultItems, isEnd)
                }

                is CustomResponse.Failure -> {
                    Timber.e(customResponse.e)
                    Pair(emptyList(), false)
                }
            }

        private suspend fun searchViaKeyword(
            query: String,
            searchPage: Int,
        ): CustomResponse =
            try {
                val response = kakaoLocalDataSource.searchViaKeyword(query, searchPage)
                CustomResponse.Success(response)
            } catch (e: Exception) {
                CustomResponse.Failure(e)
            }
    }