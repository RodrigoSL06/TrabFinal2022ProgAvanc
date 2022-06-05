package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import java.util.*

data class Jogador(
    var nome: String,
    var ncamisola: String,
    var equipa: String,
    var data_nascimento: Date,
    var telemovel: String,
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDJogador.CAMPO_NOME, nome)
        valores.put(TabelaBDJogador.CAMPO_NCAMISOLA, ncamisola)
        valores.put(TabelaBDJogador.CAMPO_EQUIPA, equipa)
        valores.put(TabelaBDJogador.CAMPO_DATA_NASCIMENTO, data_nascimento.toString())
        valores.put(TabelaBDJogador.CAMPO_TELEMOVEL, telemovel)

        return valores
    }
}