package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import java.util.*

data class Equipa(
    var nome: String,
    var localidade: String,
    var njogador: String,
    var treinador: String,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDEquipa.CAMPO_NOME, nome)
        valores.put(TabelaBDEquipa.CAMPO_LOCALIDADE, localidade)
        valores.put(TabelaBDEquipa.CAMPO_NUM_JOGADORES, njogador)
        valores.put(TabelaBDEquipa.CAMPO_TREINADOR, treinador)

        return valores
    }
}