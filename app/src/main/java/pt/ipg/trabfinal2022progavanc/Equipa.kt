package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
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

    companion object {
        fun fromCursor(cursor: Cursor): Equipa {

            val colNome = cursor.getColumnIndex(TabelaBDEquipa.CAMPO_NOME)
            val colLocalidade = cursor.getColumnIndex(TabelaBDEquipa.CAMPO_LOCALIDADE)
            val colNjogador = cursor.getColumnIndex(TabelaBDEquipa.CAMPO_NUM_JOGADORES)
            val colTreinador = cursor.getColumnIndex(TabelaBDEquipa.CAMPO_TREINADOR)
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(colNome)
            val localidade = cursor.getString(colLocalidade)
            val nJogador = cursor.getString(colNjogador)
            val treinador = cursor.getString(colTreinador)
            val id = cursor.getLong(colId)

            return Equipa(nome, localidade, nJogador, treinador, id)
        }
    }
}