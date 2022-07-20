package pt.ipg.trabfinal2022progavanc

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable


data class Jogador(

    var nome: String,
    var ncamisola: String,
    var dataNascimento: String,
    var telemovel: String,
    var id: Long = -1,
):Serializable{

    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaBDJogador.CAMPO_NOME, nome)
        valores.put(TabelaBDJogador.CAMPO_NCAMISOLA, ncamisola)
        valores.put(TabelaBDJogador.CAMPO_DATA_NASCIMENTO, dataNascimento)
        valores.put(TabelaBDJogador.CAMPO_TELEMOVEL, telemovel)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Jogador {

            val colNome = cursor.getColumnIndex(TabelaBDJogador.CAMPO_NOME)
            val colNCamisola = cursor.getColumnIndex(TabelaBDJogador.CAMPO_NCAMISOLA)
            val colDataNascimento = cursor.getColumnIndex(TabelaBDJogador.CAMPO_DATA_NASCIMENTO)
            val colTelemovel = cursor.getColumnIndex(TabelaBDJogador.CAMPO_TELEMOVEL)
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val nome = cursor.getString(colNome)
            val ncamisola = cursor.getString(colNCamisola)
            val dataNascimento = cursor.getString(colDataNascimento)
            val telemovel = cursor.getString(colTelemovel)
            val id = cursor.getLong(colId)


            return Jogador(nome, ncamisola, dataNascimento, telemovel, id)
        }
    }
}