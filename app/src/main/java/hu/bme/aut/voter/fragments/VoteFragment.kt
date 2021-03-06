package hu.bme.aut.voter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.FragmentVoteBinding

class VoteFragment : Fragment() {

    private lateinit var binding : FragmentVoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}