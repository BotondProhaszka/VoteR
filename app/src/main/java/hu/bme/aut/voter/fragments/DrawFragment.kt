package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.voter.databinding.FragmentDrawBinding

class DrawFragment : Fragment() {

    private lateinit var binding : FragmentDrawBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}