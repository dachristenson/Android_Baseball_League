package com.example.abl.standings

import android.app.Application
import androidx.lifecycle.*
import com.example.abl.data.BaseballDatabase
import com.example.abl.data.BaseballRepository
import com.example.abl.util.getErrorMessage
import kotlinx.coroutines.launch

class StandingsViewModel(application: Application):
    AndroidViewModel(application) {

    private val repo: BaseballRepository
    val errorMessage = MutableLiveData("")
    val standings: LiveData<List<UITeamStanding>>

    fun refreshStandings() {
        viewModelScope.launch {
            repo.updateStandings().getErrorMessage(getApplication())
                ?.let { message -> errorMessage.value = message }
        }
    }

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