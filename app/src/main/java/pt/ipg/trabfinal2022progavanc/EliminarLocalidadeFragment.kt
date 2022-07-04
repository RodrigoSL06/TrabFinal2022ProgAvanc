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
import pt.ipg.trabfinal2022progavanc.Localidade
import pt.ipg.trabfinal2022progavanc.ContentProviderFutsal
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabfinal2022progavanc.databinding.FragmentEliminarLocalidadeBinding

class EliminarLocalidadeFragment : Fragment() {
    private var _binding: FragmentEliminarLocalidadeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var localidade: Localidade

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarLocalidadeBinding.inflate(inflater, container, false)
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

        localidade = EliminarLocalidadeFragmentArgs.fromBundle(arguments!!).localidade

        binding.textViewNome.text = localidade.nomeLocalidade

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaLocalidade()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLocalidade()
                true
            }
            else -> false
        }

    private fun eliminaEquipa() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.delete_localidade)
            setMessage(R.string.sure)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarEquipa() })
            show()
        }
    }

    private fun confirmaEliminarEquipa() {
        val enderecoCliente = Uri.withAppendedPath(ContentProviderFutsal.ENDERECO_LOCALIDADE, "${localidade.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoCliente, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNome,
                R.string.erro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG).show()
        voltaListaLocalidade()
    }

    private fun voltaListaLocalidade() {
        val acao = EliminarLocalidadeFragmentDirections.action_eliminarLocalidadeFragment_to_secondFragment()
        findNavController().navigate(acao)
    }
}