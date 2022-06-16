package pt.ipg.trabfinal2022progavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDLocalidade(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," + "$CAMPO_NOME TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "Localidade"
        const val CAMPO_NOME = "nome"

    }
}