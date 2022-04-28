package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.databinding.FragmentJoinVoteBinding

class JoinVoteFragment : Fragment() {
    private lateinit var binding: FragmentJoinVoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinVoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}