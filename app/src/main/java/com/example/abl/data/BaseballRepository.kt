package com.example.abl.data

import androidx.lifecycle.LiveData
import com.example.abl.standings.TeamStanding
import com.example.abl.util.*
import dev.mfazio.abl.api.services.getDefaultABLService

class BaseballRepository(private val baseballDao: BaseballDao) {
    fun getStandings(): LiveData<List<TeamStanding>> =
        baseballDao.getStandings()

    suspend fun updateStandings() {
        val standingsResult = apiService.getStandings()

        // Note:  p. 247 used "standings" rather than "standingsResult" below,
        // but this didn't seem to reference anything in the provided code.
        if(standingsResult.any()) {
            baseballDao.updateStandings(
                standingsResult.convertToTeamStandings(
                    baseballDao.getCurrentStandings()
                )
            )
        }
    }

    enum class ResultStatus {
        Unknown,
        Success,
        NetworkException,
        RequestException,
        GeneralException
    }

    companion object {
        private val apiService = getDefaultABLService()

        @Volatile
        private var instance: BaseballRepository? = null

        fun getInstance(baseballDao: BaseballDao) =
            this.instance ?: synchronized(this) {
                instance ?: BaseballRepository(baseballDao).also {
                    instance = it
                }
            }
    }
}