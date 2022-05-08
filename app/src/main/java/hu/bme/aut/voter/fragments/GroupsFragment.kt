package hu.bme.aut.voter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.adapters.GroupAdapter
import hu.bme.aut.voter.databinding.FragmentGroupsBinding
import hu.bme.aut.voter.dialog.CreateGroupDialog
import hu.bme.aut.voter.interfaces.GroupsCallback
import hu.bme.aut.voter.model.Group
import hu.bme.aut.voter.services.GroupDatabase
import kotlin.concurrent.thread

class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var ownGroupsAdapter: GroupAdapter
    private lateinit var allGroupsAdapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(layoutInflater, container, false)


        binding.fabCreateNewGroup.setOnClickListener {
            createGroup()
        }
        binding.swipeRefreshOwn.setOnRefreshListener { loadOwnGroups() }
        binding.swipeRefreshLayoutAll.setOnRefreshListener { loadAllGroups() }

        initRecyclerViews()

        return binding.root
    }


    private fun initRecyclerViews() {
        ownGroupsAdapter = GroupAdapter(this.requireActivity(), true)
        allGroupsAdapter = GroupAdapter(this.requireActivity(), false)
        binding.rvOwnGroups.adapter = ownGroupsAdapter
        binding.rvAllGroups.adapter = allGroupsAdapter
        loadOwnGroups()
        loadAllGroups()
    }

    private fun loadOwnGroups() {
        thread {
            MainActivity.firestoreDatabase.getGroupsByOwner(MainActivity.user.getEmail(), this)
        }
    }
    private fun loadAllGroups() {
        thread {
            MainActivity.firestoreDatabase.getGroups(this)
        }
    }

    fun setOwnGroups(groups: List<Group>) {
        Log.d(MainActivity.TAG_BUGFIX, "setOwnGroups ${groups.size}")
        ownGroupsAdapter.submitList(groups)
        binding.swipeRefreshOwn.isRefreshing = false
        binding.tvOwnGroupsCount.text = groups.size.toString()
    }

    fun setAllGroups(groups : List<Group>){
        allGroupsAdapter.submitList(groups)
        binding.swipeRefreshLayoutAll.isRefreshing = false
        binding.tvAllGroupsCount.text = groups.size.toString()
    }

    private fun createGroup() {
        val createGroup = CreateGroupDialog()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        createGroup.show(transaction, "Create group")
    }
}