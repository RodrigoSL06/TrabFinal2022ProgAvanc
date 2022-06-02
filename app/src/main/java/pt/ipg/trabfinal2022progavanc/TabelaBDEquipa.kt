package pt.ipg.trabfinal2022progavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDEquipa(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_LOCALIDADE TEXT NOT NULL, $CAMPO_NUM_JOGADORES INTEGER NOT NULL )")
    }

    companion object {
        const val NOME_TABELA = "Equipa"
        const val CAMPO_NOME = "nome"
        const val CAMPO_LOCALIDADE = "localidade"
        const val CAMPO_NUM_JOGADORES = "num jogadores"
    }
}




