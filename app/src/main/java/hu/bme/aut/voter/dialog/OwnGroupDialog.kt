package hu.bme.aut.voter.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.adapters.UserAdapter
import hu.bme.aut.voter.databinding.DialogOwnGroupBinding
import hu.bme.aut.voter.interfaces.RemoveUser
import hu.bme.aut.voter.interfaces.UserInterface
import hu.bme.aut.voter.model.Group

class OwnGroupDialog(private val group: Group) : DialogFragment(), RemoveUser{
    private lateinit var binding: DialogOwnGroupBinding
    private lateinit var userAdapter : UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOwnGroupBinding.inflate(layoutInflater)
        binding.tvConnectionId.text = group.connectId
        binding.tvGroupName.text = group.name
        binding.btnClose.setOnClickListener { dismiss() }

        binding.btnDelete.setOnClickListener {
            MainActivity.firestoreDatabase.deleteGroup(group)
            dismiss()
        }

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView(){
        userAdapter = UserAdapter(requireContext(), true, this)
        binding.rvMembers.adapter = userAdapter
        userAdapter.submitList(group.members)
    }

    override fun removeUser(user: UserInterface) {
        group.members.remove(user)
        MainActivity.firestoreDatabase.updateGroup(group)
    }
}