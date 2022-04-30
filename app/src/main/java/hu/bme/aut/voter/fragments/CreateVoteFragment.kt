package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.adapters.GameAdapter
import hu.bme.aut.voter.services.GameDatabase
import hu.bme.aut.voter.databinding.FragmentCreateVoteBinding
import hu.bme.aut.voter.model.Game
import hu.bme.aut.voter.model.Vote
import java.lang.Exception
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.min

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
        checkUser()
        binding.btnAdd.setOnClickListener { addGame() }

        binding.btnCreate.setOnClickListener {
            createVote()
        }




        return binding.root
    }


    private fun initRecyclerView() {
        gameAdapter = GameAdapter(requireContext())
        binding.rvGame.adapter = gameAdapter
        GameDatabase.getInstance().gameDao().getAllGames()
            .observe(this.requireActivity()) { games ->
                Log.d(MainActivity.TAG_BUGFIX, games.size.toString())
                gameAdapter.submitList(games)
            }
    }

    private fun checkUser() {
        if (!MainActivity.user.hasRightCreatePoll()) {
            binding.btnCreate.isEnabled = false
            binding.btnCreate.isClickable = false
            Toast.makeText(
                requireContext(),
                "You have not right to create vote! Use other login option to have!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            binding.btnCreate.isEnabled = true
            binding.btnCreate.isClickable = true
        }
    }

    private fun addGame() {
        thread {
            val gameName = binding.etGameName.text.toString()
            if (gameName.isEmpty())
                return@thread
            val newGame = Game(gameName, true, "")
            val checkGame = GameDatabase.getInstance().gameDao().getGame(gameName)
            if (checkGame == null)
                GameDatabase.getInstance().gameDao().insertGame(newGame)

        }
    }

    private fun createVote() {

        val games = GameDatabase.getInstance().gameDao().getSelectedGames()
        games.observe(this.requireActivity()) { selectedGames ->

            val minutes = binding.etMinutesPicker.text.toString()
            val voteName = binding.etVoteName.text.toString()
            when {
                voteName.isEmpty() -> {
                    Toast.makeText(
                        this.requireContext(),
                        "Please type a name! |$voteName|",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@observe
                }
                minutes.toLong() == 0.toLong() -> {
                    Toast.makeText(
                        this.requireContext(),
                        "Please type in a correct time!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@observe
                }
                selectedGames.isEmpty() -> {
                    Toast.makeText(
                        this.requireContext(),
                        "Please select the games!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@observe
                }
                else -> {
                    val vote = Vote(
                        voteName,
                        MainActivity.user.getEmail(),
                        selectedGames,
                        MainActivity.dateTimeService.addMinutesToCurrentUtcTime(minutes.toLong()).toString()
                    )
                    thread {
                        MainActivity.firestoreDatabase.createVote(vote)
                    }
                }
            }
        }
    }
}