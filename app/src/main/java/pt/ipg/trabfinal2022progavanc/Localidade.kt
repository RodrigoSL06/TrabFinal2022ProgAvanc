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

            val colIdLocalidade = cursor.getColumnIndex(BaseColumns._ID)
            val colNomeLocalidade = cursor.getColumnIndex(TabelaBDLocalidade.CAMPO_NOME)

            val id = cursor.getLong(colIdLocalidade)
            val nome = cursor.getString(colNomeLocalidade)

            return Localidade(nome, id)
        }
    }
}