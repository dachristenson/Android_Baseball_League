package com.example.abl.standings

import android.app.Application
import androidx.lifecycle.*
import com.example.abl.data.BaseballDatabase
import com.example.abl.data.BaseballRepository

class StandingsViewModel(application: Application):
    AndroidViewModel(application) {

    private val repo: BaseballRepository

    val standings: LiveData<List<UITeamStanding>>

    init {
        repo = BaseballDatabase
            .getDatabase(application, viewModelScope)
            .baseballDao()
            .let { dao ->
                BaseballRepository.getInstance(dao)
            }

        standings =
            repo.getStandings().map { teamStandings ->
                teamStandings.mapNotNull { teamStanding ->
                    UITeamStanding.fromTeamIdAndStandings(
                        teamStanding.teamId,
                        teamStandings
                    )
                }
            }
    }
}