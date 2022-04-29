package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.adapters.GameAdapter
import hu.bme.aut.voter.data.GameDatabase
import hu.bme.aut.voter.databinding.FragmentCreateVoteBinding
import kotlinx.coroutines.MainScope

class CreateVoteFragment : Fragment() {
    private lateinit var binding: FragmentCreateVoteBinding
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateVoteBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        return binding.root
    }


    private fun initRecyclerView() {
        gameAdapter = GameAdapter(requireContext())
        binding.rvGame.adapter = gameAdapter
        GameDatabase.getInstance().gameDao().getAllGames()
            .observe(this.requireActivity()) { todos ->
                Log.d(MainActivity.TAG_BUGFIX, todos.size.toString())
                gameAdapter.submitList(todos)
            }
    }
}