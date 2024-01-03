package com.example.abl.settings

import android.os.Bundle
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.abl.R
import com.example.abl.teams.UITeam
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var favoriteTeamPreference: DropDownPreference
    private lateinit var favoriteTeamColorPreference: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val ctx = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(ctx)

        this.favoriteTeamPreference = DropDownPreference(ctx).apply {
            key = favoriteTeamPreferenceKey
            title = getString(R.string.favorite_team)
            entries = (listOf("None")
                + UITeam.allTeams.map { it.teamName }).toTypedArray()
            entryValues = (listOf("")
                + UITeam.allTeams.map { it.teamId }).toTypedArray()
            setDefaultValue("")
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        }

        this.favoriteTeamColorPreference = SwitchPreferenceCompat(ctx).apply {
            key = favoriteTeamColorsPreferenceKey
            title = getString(R.string.team_color_nav_bar)
            setDefaultValue(false)
        }

        favoriteTeamPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val teamId = newValue?.toString()

                if (favoriteTeamColorPreference.isChecked) {
                    setNavBarColorForTeam(teamId)
                }

                if (teamId != null) {
                    favoriteTeamPreference.icon = getIconForTeam(teamId)
                }

                true
            }

        screen.addPreference(favoriteTeamPreference)

        favoriteTeamColorPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val useFavoriteTeamColor = newValue as? Boolean

                setNavBarColorForTeam(
                    if (useFavoriteTeamColor == true) {
                        favoriteTeamPreference.value
                    } else null
                )

                //saveSettings(useFavoriteTeamColor = useFavoriteTeamColor)

                true
            }

        screen.addPreference(favoriteTeamColorPreference)

        preferenceScreen = screen
    }

    override fun onBindPreferences() {
        favoriteTeamPreference.icon = getIconForTeam(favoriteTeamPreference.value)
    }

    private fun getIconForTeam(teamId: String) =
        UITeam.fromTeamId(teamId)?.let { team ->
            ContextCompat.getDrawable(requireContext(), team.logoId)
        }

    private fun setNavBarColorForTeam(teamId: String?) {
        val color = UITeam.getTeamPalette(requireContext(), teamId)
            ?.dominantSwatch
            ?.rgb
            ?: getDefaultColor()

        activity?.window?.navigationBarColor = color
    }

    private fun getDefaultColor(): Int {
        val colorValue = TypedValue()

        activity?.theme?.resolveAttribute(
            android.R.attr.colorPrimary,
            colorValue,
            true
        )

        return colorValue.data
    }

    companion object {
        const val favoriteTeamPreferenceKey = "favoriteTeam"
        const val favoriteTeamColorsPreferenceKey = "useFavoriteTeamColors"
    }
}