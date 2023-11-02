package com.example.abl.scoreboard

import android.app.Application
import androidx.lifecycle.*
import com.example.abl.data.BaseballDatabase
import com.example.abl.data.BaseballRepository
import com.example.abl.teams.UITeam
import com.example.abl.utils.getErrorMessage
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScoreboardViewModel(application: Application) : AndroidViewModel(application) {
    private val currentDateTextFormat = DateTimeFormatter.ofPattern("EEEE, MMM d")

    private val repo: BaseballRepository

    private val selectedDate = MutableLiveData(LocalDate.now())

    val games: LiveData<List<ScheduledGame>>
    val teams: LiveData<List<UITeam>>
    val currentDateText: LiveData<String>
    val errorMessage = MutableLiveData("")

    init {
        repo = BaseballDatabase
            .getDatabase(application, viewModelScope)
            .baseballDao()
            .let { dao ->
                BaseballRepository.getInstance(dao)
            }

        games = Transformations.switchMap(selectedDate) { selectedDate ->
            refreshScores(selectedDate)
            repo.getGamesForDate(selectedDate)
        }

        teams = Transformations.map(games) { scheduledGames ->
            scheduledGames.flatMap { game ->
                UITeam.fromTeamIds(game.homeTeamId, game.awayTeamId)
            }.filterNotNull()
        }

        currentDateText = Transformations.map(selectedDate) { currentDate ->
            currentDateTextFormat.format(currentDate)
        }
    }

    fun goToDate(daysToMove: Long = 0, monthsToMove: Long? = null) {
        selectedDate.value?.let { date ->
            selectedDate.value = if (monthsToMove != null) {
                date.plusMonths(monthsToMove)
            } else {
                date.plusDays(daysToMove)
            }
        }
    }

    private fun refreshScores(date: LocalDate) {
        viewModelScope.launch {
            repo.updateGamesForDate(date).getErrorMessage(getApplication())?.let { message ->
                errorMessage.value = message
            }
        }
    }
}