package com.example.abl.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.NavGraphDirections
import com.example.abl.R
import com.example.abl.databinding.TeamsGridItemBinding

class TeamsGridAdapter(
    private val teams: List<UITeam>
) : RecyclerView.Adapter<TeamsGridAdapter.TeamViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):TeamViewHolder =
        TeamViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.teams_grid_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: TeamViewHolder,
        position: Int
    ) {
        holder.bind(teams[position])
    }

    override fun getItemCount(): Int = teams.size

    inner class TeamViewHolder(private val binding: TeamsGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UITeam) {
            binding.apply {
                team = item

                // I replaced "setClickListener {}" with "root.setOnClickListener {}",
                // as suggested by Michael Fazio via DevTalk.  Using the provided
                // code with "setClickListener {}" would not build, and adding the
                // function import suggested by Android Studio just threw parameter
                // errors.  The body within the braces remains unchanged.
                root.setOnClickListener { view ->
                    val action = NavGraphDirections
                        .actionGoToTeam(item.teamId, item.teamName)
                    view.findNavController().navigate(action)
                }
            }
        }
    }
}
