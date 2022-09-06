package com.tcl.characterapp.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tcl.characterapp.data.remote.CharacterApi
import com.tcl.characterapp.data.remote.character.CharacterData
import javax.inject.Inject

const val STARTING_PAGE_INDEX = 1

class CharactersPagingDataSource @Inject constructor(
    private val characterApi: CharacterApi,
    private val nameQuery: String
) : PagingSource<Int, CharacterData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                characterApi.getAllCharacters(
                    page = pageNumber,
                    name = nameQuery
                )

            val data = response.results

            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return null
    }


}