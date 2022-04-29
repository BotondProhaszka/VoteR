package hu.bme.aut.voter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.pack.IconPack
import hu.bme.aut.voter.adapters.GameAdapter
import hu.bme.aut.voter.data.Game
import hu.bme.aut.voter.data.GameDatabase
import hu.bme.aut.voter.databinding.FragmentDrawBinding
import hu.bme.aut.voter.services.IconPackService
import kotlin.concurrent.thread


class DrawFragment() : Fragment(), IconDialog.Callback {

    private lateinit var binding: FragmentDrawBinding
    private lateinit var iconPackService: IconPackService
    private lateinit var gameAdapter: GameAdapter

    //https://github.com/maltaisn/icondialoglib

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        iconPackService = IconPackService(this.requireContext())
        val iconDialog = childFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())
        binding.ivIcon.setOnClickListener {
            iconDialog.show(childFragmentManager, ICON_DIALOG_TAG)
        }
        binding.btnAdd.setOnClickListener {
            thread { addGame() }
        }
        binding.btnDraw.setOnClickListener {
            thread { draw() }
        }
        return binding.root
    }

    override val iconDialogIconPack: IconPack
        get() = iconPackService.iconPack

    override fun onIconDialogIconsSelected(
        dialog: IconDialog,
        icons: List<com.maltaisn.icondialog.data.Icon>
    ) {
        Toast.makeText(this.context, "Icons selected: ${icons.map { it.id }}", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        private const val ICON_DIALOG_TAG = "icon-dialog"
    }


    private fun addGame() {
        val newGame = Game(null, binding.etGameName.text.toString(), true, "")
        GameDatabase.getInstance(requireContext()).gameDao().insertGame(newGame)
    }

    private fun initRecyclerView() {
        gameAdapter = GameAdapter(requireContext())
        binding.rvGame.adapter = gameAdapter
        GameDatabase.getInstance(requireContext()).gameDao().getAllGames()
            .observe(this.requireActivity()) { todos ->
                gameAdapter.submitList(todos)
            }
    }

    private fun draw() {
        val selectedGames = GameDatabase.getInstance(requireContext()).gameDao().getSelectedGames()

        this.requireActivity().runOnUiThread {

            selectedGames.observe(this.requireActivity()) {
                todos -> binding.tvResult.text = todos.random().gameName
            }
        }
    }
}