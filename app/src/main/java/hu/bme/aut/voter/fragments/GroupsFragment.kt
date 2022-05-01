package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.adapters.GroupAdapter
import hu.bme.aut.voter.databinding.FragmentGroupsBinding
import hu.bme.aut.voter.interfaces.GroupsCallback
import hu.bme.aut.voter.model.Group

class GroupsFragment : Fragment(), GroupsCallback {
    private lateinit var binding : FragmentGroupsBinding
    private lateinit var groupAdapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(layoutInflater, container, false)

        binding.swipeRefresh.setOnRefreshListener {
            loadGroups()
        }

        initRecyclerView()

        return binding.root
    }


    private fun initRecyclerView(){
        groupAdapter = GroupAdapter(requireContext())
        binding.rvGroups.adapter = groupAdapter
    }

    private fun loadGroups(){
        MainActivity.firestoreDatabase.getGroups(this)
    }

    override fun groupsReadyListener(groups: List<Group>) {
        groupAdapter.submitList(groups)
        binding.swipeRefresh.isRefreshing = false
    }
}