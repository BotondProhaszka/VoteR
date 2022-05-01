package hu.bme.aut.voter.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.voter.databinding.FragmentCreateVoteBinding

class CreateGroupDialog : DialogFragment() {

    private lateinit var binding : FragmentCreateVoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateVoteBinding.inflate(layoutInflater)


        return binding.root
    }

}