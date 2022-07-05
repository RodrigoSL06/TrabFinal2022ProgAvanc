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
import pt.ipg.trabfinal2022progavanc.databinding.FragmentEditarJogadorBinding


class EditarJogadorFragment : Fragment(){
    private var _binding: FragmentEditarJogadorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var jogador: Jogador? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarJogadorBinding.inflate(inflater, container, false)
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
            jogador = EditarJogadorFragmentArgs.fromBundle(arguments!!).jogador

            if (jogador != null) {
                binding.editTextNomeEquipa.setText(jogador!!.nome)
                binding.editTextNCamisola.setText(jogador!!.ncamisola!!)
                binding.editTextNomeEquipa.setText(jogador!!.nomeEquipa!!)
                binding.editTextDataNascimento.setText(jogador!!.dataNascimento!!)
                binding.editTextTelemovel.setText(jogador!!.telemovel!!)

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
        val nome = binding.editTextNome.text.toString()
        if (nome.isBlank()) {
            binding.editTextNome.error = getString(R.string.Mandatoryfield)
            binding.editTextNome.requestFocus()
            return
        }

        val nCamisola = binding.editTextNCamisola.text.toString()
        if (nCamisola.isBlank()) {
            binding.editTextNCamisola.error = getString(R.string.Mandatoryfield)
            binding.editTextNCamisola.requestFocus()
            return
        }

        val equipa = binding.editTextNomeEquipa.text.toString()
        if (equipa.isBlank()) {
            binding.editTextNomeEquipa.error = getString(R.string.Mandatoryfield)
            binding.editTextNomeEquipa.requestFocus()
            return
        }

        val dataNascimento = binding.editTextDataNascimento.text.toString()
        if (dataNascimento.isBlank()) {
            binding.editTextDataNascimento.error = getString(R.string.Mandatoryfield)
            binding.editTextDataNascimento.requestFocus()
            return
        }

        val telemovel = binding.editTextTelemovel.text.toString()
        if (telemovel.isBlank()) {
            binding.editTextTelemovel.error = getString(R.string.Mandatoryfield)
            binding.editTextTelemovel.requestFocus()
            return
        }


        val EquipaGuardado =
            if (jogador== null) {
                insereJogador(nome, nCamisola, equipa, dataNascimento, telemovel)
            } else {
                alteraJogador(nome, nCamisola, equipa, dataNascimento, telemovel)
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

    private fun alteraJogador(nome: String, ncamisola: String, idEquipa: Long, nomeEquipa: String, dataNascimento: String, telemovel: String) : Boolean {
        val jogador = Jogador(nome, ncamisola, idEquipa ,nomeEquipa,dataNascimento , telemovel)

        val enderecoJogador = Uri.withAppendedPath(ContentProviderFutsal.ENDERECO_JOGADORES, "${this.jogador!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoJogador, jogador.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereJogador(nome: String, ncamisola: String, idEquipa: Long, nomeEquipa: String, dataNascimento: String, telemovel: String) : Boolean {
        val jogador = Jogador(nome, ncamisola, idEquipa ,nomeEquipa,dataNascimento , telemovel)

        val enderecoJogadorInserido = requireActivity().contentResolver.insert(ContentProviderFutsal.ENDERECO_JOGADORES, jogador.toContentValues())

        return enderecoJogadorInserido != null
    }

    private fun voltaListaEquipas() {
        findNavController().navigate(R.id.action_editarEquipaFragment_to_listarEquipasFragment)
    }


}