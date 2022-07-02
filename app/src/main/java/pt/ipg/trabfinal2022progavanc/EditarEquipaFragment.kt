package pt.ipg.trabfinal2022progavanc

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.ipg.trabfinal2022progavanc.Equipa
import pt.ipg.trabfinal2022progavanc.ContentProviderFutsal
import pt.ipg.trabfinal2022progavanc.databinding.FragmentEditarEquipaBinding
import com.google.android.material.snackbar.Snackbar


class EditarEquipaFragment : Fragment(){
    private var _binding: FragmentEditarEquipaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var equipa: Equipa? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarEquipaBinding.inflate(inflater, container, false)
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
            equipa = EditarEquipaFragmentArgs.fromBundle(arguments!!).equipa

            if (equipa != null) {
                binding.editTextNomeEquipa.setText(equipa!!.nomeEquipa)
                binding.textViewLocal.setText(equipa!!.localidade)

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
                voltaListaEquipas()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome = binding.editTextNomeEquipa.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeEquipa.error = getString(R.string.campo_obrigatorio)
            binding.editTextNomeEquipa.requestFocus()
            return
        }

        val localidade = binding.spinnerLocal.selectedItemId
        if (localidade == Spinner.INVALID_ROW_ID) {
            binding.textViewLocal.error = getString(R.string.field_mandatory)
            binding.spinnerLocal.requestFocus()
            return
        }

        val EquipaGuardado =
            if (equipa== null) {
                insereEquipa(nome, localidade)
            } else {
                alteraEquipa(nome, localidade)
            }

        if (EquipaGuardado) {
            Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG)
                .show()
            voltaListaEquipas()
        } else {
            Snackbar.make(binding.editTextNomeEquipa, R.string.error, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraEquipa(nomeEquipa: String, localidade: Long) : Boolean {
        val equipa = Equipa(nomeEquipa, localidade)

        val enderecoEquipa = Uri.withAppendedPath(ContentProviderFutsal.ENDERECO_EQUIPA, "${this.equipa!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoEquipa, equipa.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereEquipa(nomeEquipa: String, localidade: Long ): Boolean {
        val equipa = Equipa(nomeEquipa, localidade)

        val enderecoEquipaInserido = requireActivity().contentResolver.insert(ContentProviderFutsal.ENDERECO_EQUIPA, equipa.toContentValues())

        return enderecoEquipaInserido != null
    }

    private fun voltaListaEquipas() {
        findNavController().navigate(R.id.action_editarEquipaFragment_to_SecondFragment)
    }


}