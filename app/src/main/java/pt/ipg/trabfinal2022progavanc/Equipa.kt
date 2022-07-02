package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Equipa(
    var nomeEquipa: String,
    var localidade: Long,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDEquipa.CAMPO_NOME, nomeEquipa)
        valores.put(TabelaBDEquipa.CAMPO_LOCALIDADE_ID, localidade)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Equipa {

            val colNome = cursor.getColumnIndex(TabelaBDEquipa.CAMPO_NOME)
            val colIDLocalidade = cursor.getColumnIndex(TabelaBDEquipa.CAMPO_LOCALIDADE_ID)
            val colNomeLocalidade = cursor.getColumnIndex(TabelaBDLocalidade.CAMPO_NOME)
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(colNome)
            val IdLocalidade = cursor.getLong(colIDLocalidade)
            val NomeLocalidade = cursor.getString(colNomeLocalidade)
            val id = cursor.getLong(colId)

            val localidade = Localidade(NomeLocalidade, IdLocalidade)

            return Equipa(nome, localidade.id, id)
        }
    }
}