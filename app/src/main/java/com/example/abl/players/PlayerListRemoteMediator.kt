package com.example.abl.players

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.abl.data.BaseballDatabase
import dev.mfazio.abl.api.services.AndroidBaseballLeagueService

@ExperimentalPagingApi
class PlayerListRemoteMediator(
    private val apiService: AndroidBaseballLeagueService,
    private val baseballDatabase: BaseballDatabase,
    private val teamId: String? = null,
    private val nameQuery: String? = null
) : RemoteMediator<Int, PlayerListItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlayerListItem>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}
