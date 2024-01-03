package com.example.abl.leaders

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.databinding.FragmentLeadersListBinding
import com.example.abl.leaders.LeadersFragment.Companion.LEADER_TYPE_KEY

class LeadersListFragment(/*private val leaderType: LeaderType? = LeaderType.Batting*/) : Fragment() {
    private val leadersListViewModel by activityViewModels<LeadersListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLeadersListBinding.inflate(inflater)
        val leadersAdapter = LeadersAdapter()

        val leaderType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(LEADER_TYPE_KEY, LeaderType::class.java)
        } else {
            arguments?.getSerializable(LEADER_TYPE_KEY) as? LeaderType
        } ?: LeaderType.Batting

        with(binding.leadersList) {
            adapter = leadersAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        val leadersList =
            if (leaderType == LeaderType.Batting) {
                leadersListViewModel.battingLeaders
            } else {
                leadersListViewModel.pitchingLeaders
            }

        binding.leadersSwipeRefreshLayout.setOnRefreshListener {
            if (leaderType == LeaderType.Batting) {
                leadersListViewModel.refreshBattingLeaders()
            } else {
                leadersListViewModel.refreshPitchingLeaders()
            }
        }

        leadersList.observe(viewLifecycleOwner) { leaders ->
            leadersAdapter.submitList(leaders)
            binding.leadersSwipeRefreshLayout.isRefreshing = false
        }

        leadersListViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            binding.leadersSwipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }
}