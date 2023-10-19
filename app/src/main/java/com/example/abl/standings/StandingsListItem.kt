package com.example.abl.standings

import com.example.abl.teams.Division

sealed class StandingsListItem {
    abstract val id: String

    data class TeamItem(val uiTeamStanding: UITeamStanding) : StandingsListItem() {
        override val id = uiTeamStanding.teamId
    }

    data class Header(val division: Division) : StandingsListItem() {
        override val id = division.name
    }
}