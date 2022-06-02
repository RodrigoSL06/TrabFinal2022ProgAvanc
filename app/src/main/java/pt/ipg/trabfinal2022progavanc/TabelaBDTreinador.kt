package pt.ipg.trabfinal2022progavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDTreinador(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL,  $CAMPO_EQUIPA TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO INTEGER NOT NULL, $CAMPO_TELEMOVEL INTEGER NOT NULL )")
    }

    companion object {
        const val NOME_TABELA = "Treinador"
        const val CAMPO_NOME = "nome"
        const val CAMPO_EQUIPA = "equipa"
        const val CAMPO_DATA_NASCIMENTO = "data nascimento"
        const val CAMPO_TELEMOVEL = "telemovel"
    }
}