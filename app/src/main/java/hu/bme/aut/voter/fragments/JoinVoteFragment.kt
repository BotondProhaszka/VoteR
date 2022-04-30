package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.adapters.VoteAdapter
import hu.bme.aut.voter.databinding.FragmentJoinVoteBinding
import hu.bme.aut.voter.interfaces.FirebaseCallback
import hu.bme.aut.voter.model.Vote


class JoinVoteFragment : Fragment(), FirebaseCallback {
    private lateinit var binding: FragmentJoinVoteBinding
    private lateinit var voteAdapter: VoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinVoteBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        getVoteList()

        binding.swipeRefresh.setOnRefreshListener {
            getVoteList()
        }
        return binding.root
    }

    private fun initRecyclerView(){
        voteAdapter = VoteAdapter(requireContext())
        binding.rvVote.adapter = voteAdapter

    }

    private fun getVoteList(){
        MainActivity.firestoreDatabase.getVotes(this)
    }

    override fun votesReadyListener(votes: List<Vote>) {
        voteAdapter.submitList(votes)
        binding.swipeRefresh.isRefreshing = false
    }
}