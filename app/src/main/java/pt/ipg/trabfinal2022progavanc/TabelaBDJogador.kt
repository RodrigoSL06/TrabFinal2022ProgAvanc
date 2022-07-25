package pt.ipg.trabfinal2022progavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDJogador(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CAMPO_NOME TEXT NOT NULL, " +
                    "$CAMPO_NCAMISOLA INTEGER NOT NULL, " +
                    "$CAMPO_DATA_NASCIMENTO TEXT NOT NULL, " +
                    "$CAMPO_TELEMOVEL TEXT NOT NULL)")
    }



    companion object {
        const val NOME_TABELA = "Jogador"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_NCAMISOLA = "numCamisola"
        const val CAMPO_DATA_NASCIMENTO = "dataNascimento"
        const val CAMPO_TELEMOVEL = "telemovel"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_NCAMISOLA, CAMPO_DATA_NASCIMENTO, CAMPO_TELEMOVEL)
    }
}