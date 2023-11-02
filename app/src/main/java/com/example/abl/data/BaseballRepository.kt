package com.example.abl.data

import androidx.lifecycle.LiveData
import com.example.abl.standings.TeamStanding

class BaseballRepository(private val baseballDao: BaseballDao) {
    fun getStandings(): LiveData<List<TeamStanding>> =
        baseballDao.getStandings()

    companion object {
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