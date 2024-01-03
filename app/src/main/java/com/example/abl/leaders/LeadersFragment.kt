package com.example.abl.leaders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.abl.databinding.FragmentLeadersBinding
import com.example.abl.leaders.LeadersFragment.Companion.LEADER_TYPE_KEY
import java.lang.IndexOutOfBoundsException

const val BATTING_LEADERS_FRAGMENT_INDEX = 0
const val PITCHING_LEADERS_FRAGMENT_INDEX = 1

class LeadersFragment : Fragment() {

    private lateinit var leadersViewPager: ViewPager2
    private lateinit var leadersTabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLeadersBinding.inflate(inflater)

        this.leadersViewPager = binding.leadersViewPager
        this.leadersTabLayout = binding.leadersTabLayout

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val leadersListAdapter = LeadersListAdapter(this)

        if(this::leadersViewPager.isInitialized && this::leadersTabLayout.isInitialized) {
            this.leadersViewPager.adapter = leadersListAdapter

            TabLayoutMediator(this.leadersTabLayout, this.leadersViewPager) { tab, position ->
                tab.text = when (position) {
                    BATTING_LEADERS_FRAGMENT_INDEX -> "Batting"
                    PITCHING_LEADERS_FRAGMENT_INDEX -> "Pitching"
                    else -> "N/A"
                }
            }.attach()
        }
    }

    companion object {
        const val LEADER_TYPE_KEY = "leaderType"
    }
}

class LeadersListAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val leaderTypeMap: Map<Int, LeaderType> = mapOf(
        BATTING_LEADERS_FRAGMENT_INDEX to LeaderType.Batting,
        PITCHING_LEADERS_FRAGMENT_INDEX to LeaderType.Pitching
    )

    override fun getItemCount(): Int = leaderTypeMap.size

    override fun createFragment(position: Int): Fragment =
        leaderTypeMap[position]?.let { leaderType ->
            //LeadersListFragment(leaderType)
            LeadersListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(LEADER_TYPE_KEY, leaderType)
                }
            }
        } ?: throw IndexOutOfBoundsException()
}

enum class LeaderType { Batting, Pitching }