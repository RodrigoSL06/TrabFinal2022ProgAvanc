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
                    //"$CAMPO_DATA_NASCIMENTO TEXT NOT NULL, " +
                    "$CAMPO_TELEMOVEL TEXT NOT NULL," +
                    "FOREIGN KEY ($CAMPO_EXTERNO_NOME_EQUIPA) REFERENCES ${TabelaBDEquipa.CAMPO_NOME} ON DELETE RESTRICT, " +
                    "FOREIGN KEY ($CAMPO_EQUIPA_ID) REFERENCES ${TabelaBDEquipa.NOME_TABELA} (${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    /*
    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val ultimaColuna = columns.size - 1

        var posColNomeEquipa = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_EQUIPA) {
                posColNomeEquipa = i
                break
            }
        }

        if (posColNomeEquipa == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeEquipa) {
                "${TabelaBDEquipa.NOME_TABELA}.${TabelaBDEquipa.CAMPO_NOME} AS ${CAMPO_EXTERNO_NOME_EQUIPA}"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "${NOME_TABELA} INNER JOIN ${TabelaBDEquipa.NOME_TABELA} ON ${TabelaBDEquipa.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_EQUIPA_ID}"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }



    */
    companion object {
        const val NOME_TABELA = "Treinador"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_EQUIPA_ID = "equipaID"
        const val CAMPO_EXTERNO_NOME_EQUIPA = "equipa"
        // const val CAMPO_DATA_NASCIMENTO = "dataNascimento"
        const val CAMPO_TELEMOVEL = "telemovel"


        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_EQUIPA_ID, CAMPO_EXTERNO_NOME_EQUIPA, CAMPO_TELEMOVEL)
    }
}