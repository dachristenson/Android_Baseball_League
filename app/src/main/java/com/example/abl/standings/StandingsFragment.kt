package com.example.abl.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.databinding.FragmentStandingsBinding

class StandingsFragment : Fragment() {

    private val standingsViewModel by activityViewModels<StandingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStandingsBinding.inflate(inflater)
        val standingsAdapter = StandingsAdapter()

        binding.standingsList.adapter = standingsAdapter

        binding.standingsSwipeRefreshLayout.setOnRefreshListener {
            standingsViewModel.refreshStandings()
        }

        standingsViewModel.standings.observe(viewLifecycleOwner) { standings ->
            standingsAdapter.addHeadersAndBuildStandings(standings)
            binding.standingsSwipeRefreshLayout.isRefreshing = false
        }

        standingsViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if(!errorMessage.isNullOrEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            binding.standingsSwipeRefreshLayout.isRefreshing = false
        }

        standingsViewModel.refreshStandings()

        return binding.root
    }
}