package pt.ipg.trabfinal2022progavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDEquipa(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CAMPO_NOME TEXT NOT NULL, $CAMPO_LOCALIDADE TEXT NOT NULL, $CAMPO_NUM_JOGADORES INTEGER NOT NULL, $CAMPO_TREINADOR TEXT NOT NULL )")
    }



    companion object {
        const val NOME_TABELA = "Equipa"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_LOCALIDADE = "localidade"
        const val CAMPO_NUM_JOGADORES = "num jogadores"
        const val CAMPO_TREINADOR = " treinador"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_LOCALIDADE, CAMPO_NUM_JOGADORES, CAMPO_TREINADOR)
    }
}




