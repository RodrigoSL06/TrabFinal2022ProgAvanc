package pt.ipg.trabfinal2022progavanc

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pt.ipg.trabfinal2022progavanc.Equipa
import pt.ipg.trabfinal2022progavanc.ContentProviderFutsal
import pt.ipg.trabfinal2022progavanc.databinding.FragmentEliminarEquipaBinding
import com.google.android.material.snackbar.Snackbar


class EliminarEquipaFragment : Fragment() {
    private var _binding: FragmentEliminarEquipaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var equipa: Equipa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarEquipaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_elimina

        equipa = EliminarEquipaFragmentArgs.fromBundle(arguments!!).equipa

        binding.textViewNome.text = equipa.nomeEquipa
        binding.textViewContacto.text = equipa.fgdsd

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaEquipa()
                true
            }
            R.id.action_cancelar -> {
                voltaListaEquipa()
                true
            }
            else -> false
        }

    private fun eliminaEquipa() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.deleteTeam)
            setMessage(R.string.confirmdeletion)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarEquipa() })
            show()
        }
    }

    private fun confirmaEliminarEquipa() {
        val enderecoEquipa = Uri.withAppendedPath(ContentProviderFutsal.ENDERECO_EQUIPA, "${equipa.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoEquipa, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNome,
                R.string.error,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG).show()
        voltaListaEquipa()
    }

    private fun voltaListaEquipa() {
        val acao = EliminarEquipaFragmentDirections.actionEliminarEquipaFragmentToListarEquipasFragment()
        findNavController().navigate(acao)
    }
}