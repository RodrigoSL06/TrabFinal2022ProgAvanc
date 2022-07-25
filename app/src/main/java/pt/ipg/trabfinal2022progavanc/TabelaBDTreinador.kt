package pt.ipg.trabfinal2022progavanc


import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaBDTreinador(db: SQLiteDatabase) : TabelasBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL(
            "CREATE TABLE $nome (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CAMPO_NOME TEXT NOT NULL, " +
                    "$CAMPO_DATA_NASCIMENTO TEXT NOT NULL, " +
                    "$CAMPO_TELEMOVEL TEXT NOT NULL)")
    }


    companion object {
        const val NOME_TABELA = "Treinador"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DATA_NASCIMENTO = "dataNascimento"
        const val CAMPO_TELEMOVEL = "telemovel"


        val TODAS_COLUNAS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_TELEMOVEL)
    }
}