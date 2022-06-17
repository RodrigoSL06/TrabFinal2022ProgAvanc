package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
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

    companion object {
        fun fromCursor(cursor: Cursor): Localidade {

            val colNome = cursor.getColumnIndex(TabelaBDLocalidade.CAMPO_NOME)
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(colNome)
            val id = cursor.getLong(colId)

            return Localidade(nome, id)
        }
    }
}