package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import java.util.*

data class Localidade(
    var nome: String,

) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDJogador.CAMPO_NOME, nome)

        return valores
    }
}