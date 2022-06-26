package pt.ipg.trabfinal2022progavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDTreinador(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CAMPO_NOME TEXT NOT NULL, $CAMPO_EQUIPA_ID INTEGER NOT NULL, FOREIGN KEY ($CAMPO_EQUIPA_ID) REFERENCES ${TabelaBDEquipa.NOME_TABELA} ON DELETE RESTRICT," +
                    "$CAMPO_DATA_NASCIMENTO TEXT NOT NULL, $CAMPO_TELEMOVEL INTEGER NOT NULL)")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME_TABELA INNER JOIN ${TabelaBDEquipa.NOME_TABELA} ON ${TabelaBDEquipa.NOME_TABELA}.${BaseColumns._ID} = $CAMPO_EQUIPA_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    companion object {
        const val NOME_TABELA = "Treinador"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_EQUIPA_ID = "equipaID"
        const val CAMPO_DATA_NASCIMENTO = "data nascimento"
        const val CAMPO_TELEMOVEL = "telemovel"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_EQUIPA_ID, TabelaBDEquipa.CAMPO_NOME, CAMPO_DATA_NASCIMENTO, CAMPO_TELEMOVEL)
    }
}