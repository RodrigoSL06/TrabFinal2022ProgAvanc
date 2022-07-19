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
import pt.ipg.trabfinal2022progavanc.databinding.FragmentEliminarJogadorBinding


class EliminarJogadorFragment : Fragment() {
    private var _binding: FragmentEliminarJogadorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var jogador: Jogador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarJogadorBinding.inflate(inflater, container, false)
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

        jogador = EliminarJogadorFragmentArgs.fromBundle(arguments!!).jogador

        binding.textViewNome.text = jogador.nome
        binding.textViewNumber.text = jogador.ncamisola
        binding.textViewBirth.text = jogador.dataNascimento
        binding.textViewPhone.text = jogador.telemovel

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaJogador()
                true
            }
            R.id.action_cancelar -> {
                voltaListaJogador()
                true
            }
            else -> false
        }

    private fun eliminaJogador() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.deletePlayer)
            setMessage(R.string.confirmdeletion)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarJogador() })
            show()
        }
    }

    private fun confirmaEliminarJogador() {
        val enderecoJogador= Uri.withAppendedPath(ContentProviderFutsal.ENDERECO_JOGADORES, "${jogador.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoJogador, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNome,
                R.string.error,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG).show()
        voltaListaJogador()
    }

    private fun voltaListaJogador() {
        val acao = EliminarJogadorFragmentDirections.actionEliminarJogadorFragmentToSecondFragment2()
        findNavController().navigate(acao)
    }
}