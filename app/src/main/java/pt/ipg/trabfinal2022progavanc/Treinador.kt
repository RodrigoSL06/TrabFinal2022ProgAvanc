package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import java.util.*

data class Treinador(
    var nome: String,
    var equipa: String,
    var data_nascimento: Date,
    var telemovel: Long,
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDTreinador.CAMPO_NOME, nome)
        valores.put(TabelaBDTreinador.CAMPO_EQUIPA, equipa)
        valores.put(TabelaBDTreinador.CAMPO_DATA_NASCIMENTO, data_nascimento.toString())
        valores.put(TabelaBDTreinador.CAMPO_TELEMOVEL, telemovel)

        return valores
    }
}
