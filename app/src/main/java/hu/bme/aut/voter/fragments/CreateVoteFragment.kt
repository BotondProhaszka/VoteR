package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.databinding.FragmentCreateVoteBinding

class CreateVoteFragment : Fragment() {
        private lateinit var binding : FragmentCreateVoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateVoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}