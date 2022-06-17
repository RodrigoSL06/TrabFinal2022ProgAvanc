package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import java.util.*

data class Localidade(
    var nome: String,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDJogador.CAMPO_NOME, nome)

        return valores
    }
}