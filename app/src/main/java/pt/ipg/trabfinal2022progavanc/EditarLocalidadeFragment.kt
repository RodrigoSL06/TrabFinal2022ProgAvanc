package pt.ipg.trabfinal2022progavanc

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.ipg.trabfinal2022progavanc.Localidade
import pt.ipg.trabfinal2022progavanc.ContentProviderFutsal
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabfinal2022progavanc.databinding.FragmentEditarLocalidadeBinding


class EditarLocalidadeFragment : Fragment(){
    private var _binding: FragmentEditarLocalidadeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var localidade: Localidade? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarLocalidadeBinding.inflate(inflater, container, false)
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
        activity.idMenuAtual = R.menu.menu_edicao

        if (arguments != null) {
            localidade = EditarLocalidadeFragmentArgs.fromBundle(arguments!!).localidade

            if (localidade != null) {
                binding.editTextNomeLocalidade.setText(localidade!!.)
            }
        }

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLocalidades()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome = binding.editTextNomeLocalidade.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeLocalidade.error = getString(R.string.campo_obrigatorio)
            binding.editTextNomeLocalidade.requestFocus()
            return
        }

        val LocalidadeGuardado =
            if (localidade== null) {
                insereLocalidade(nome)
            } else {
                alteraLocalidade(nome)
            }

        if (LocalidadeGuardado) {
            Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG)
                .show()
            voltaListaLocalidades()
        } else {
            Snackbar.make(binding.editTextNomeLocalidade, R.string.error, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraLocalidade(nomeLocalidade: String) : Boolean {
        val localidade = Localidade(nomeLocalidade)

        val enderecoLocalidade = Uri.withAppendedPath(ContentProviderFutsal.ENDERECO_LOCALIDADE, "${this.localidade!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoLocalidade, localidade.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereLocalidade(nomeLocalidade: String ): Boolean {
        val localidade = Localidade(nomeLocalidade)

        val enderecoLocalidadeInserido = requireActivity().contentResolver.insert(ContentProviderFutsal.ENDERECO_LOCALIDADE, localidade.toContentValues())

        return enderecoLocalidadeInserido != null
    }

    private fun voltaListaLocalidades() {
        findNavController().navigate(R.id.action_editarLocalidadeFragment_to_secondFragment)
    }


}