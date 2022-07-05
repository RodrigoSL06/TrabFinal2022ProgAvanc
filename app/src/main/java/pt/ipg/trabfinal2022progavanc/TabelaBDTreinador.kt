package pt.ipg.trabfinal2022progavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDTreinador(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CAMPO_NOME TEXT NOT NULL, $CAMPO_EQUIPA_ID INTEGER NOT NULL, " +
                    "$CAMPO_EXTERNO_NOME_EQUIPA TEXT NOT NULL," +
                    "$CAMPO_DATA_NASCIMENTO TEXT NOT NULL, " +
                    "$CAMPO_TELEMOVEL TEXT NOT NULL," +
                    "FOREIGN KEY ($CAMPO_EXTERNO_NOME_EQUIPA) REFERENCES ${TabelaBDEquipa.CAMPO_NOME} ON DELETE RESTRICT, " +
                    "FOREIGN KEY ($CAMPO_EQUIPA_ID) REFERENCES ${TabelaBDEquipa.NOME_TABELA} (${BaseColumns._ID}) ON DELETE RESTRICT)")
    }


    companion object {
        const val NOME_TABELA = "Treinador"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_EQUIPA_ID = "equipaID"
        const val CAMPO_EXTERNO_NOME_EQUIPA = "equipa"
        const val CAMPO_DATA_NASCIMENTO = "dataNascimento"
        const val CAMPO_TELEMOVEL = "telemovel"


        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_EQUIPA_ID, CAMPO_EXTERNO_NOME_EQUIPA, CAMPO_TELEMOVEL)
    }
}