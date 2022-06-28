package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Treinador(
    var nome: String,
    var idEquipa: Long,
    var data_nascimento: String,
    var telemovel: String,
    var id: Long = -1,
) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDTreinador.CAMPO_NOME, nome)
        valores.put(TabelaBDTreinador.CAMPO_EQUIPA_ID, idEquipa)
        valores.put(TabelaBDTreinador.CAMPO_DATA_NASCIMENTO, data_nascimento)
        valores.put(TabelaBDTreinador.CAMPO_TELEMOVEL, telemovel)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Treinador {

            val colNome = cursor.getColumnIndex(TabelaBDTreinador.CAMPO_NOME)
            val colIdEquipa = cursor.getColumnIndex(TabelaBDTreinador.CAMPO_EQUIPA_ID)
            val colDataNascimento = cursor.getColumnIndex(TabelaBDTreinador.CAMPO_DATA_NASCIMENTO)
            val colTelemovel = cursor.getColumnIndex(TabelaBDTreinador.CAMPO_TELEMOVEL)
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(colNome)
            val IdEquipa = cursor.getLong(colIdEquipa)
            val dataNascimento = cursor.getString(colDataNascimento)
            val telemovel = cursor.getString(colTelemovel)
            val id = cursor.getLong(colId)

            return Treinador(nome, IdEquipa, dataNascimento, telemovel, id )

        }
    }
}
