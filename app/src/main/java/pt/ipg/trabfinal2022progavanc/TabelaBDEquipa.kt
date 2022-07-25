package pt.ipg.trabfinal2022progavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaBDEquipa(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CAMPO_NOME_EQUIPA TEXT NOT NULL, $CAMPO_LOCALIDADE_ID INTEGER NOT NULL, " +
                    "FOREIGN KEY ($CAMPO_LOCALIDADE_ID) REFERENCES ${TabelaBDLocalidade.NOME_TABELA}" +
                    "(${BaseColumns._ID}) ON DELETE RESTRICT)")
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
        queryBuilder.tables = "$NOME_TABELA INNER JOIN ${TabelaBDLocalidade.NOME_TABELA} ON ${TabelaBDLocalidade.CAMPO_ID} = $CAMPO_LOCALIDADE_ID"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME_TABELA = "Equipa"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME_EQUIPA = "nome"
        const val CAMPO_LOCALIDADE_ID = "localidadeID"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME_EQUIPA, CAMPO_LOCALIDADE_ID, TabelaBDLocalidade.CAMPO_NOME)
    }
}




