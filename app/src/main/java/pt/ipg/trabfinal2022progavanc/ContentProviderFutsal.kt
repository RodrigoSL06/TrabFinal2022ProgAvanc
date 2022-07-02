package pt.ipg.trabfinal2022progavanc

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.content.UriMatcher
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderFutsal : ContentProvider() {
    var dbOpenHelper : BDFutsalOpenHelper? = null

    override fun onCreate(): Boolean {
        dbOpenHelper = BDFutsalOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper!!.readableDatabase

        requireNotNull(projection)
        val colunas = projection as Array<String>

        val argsSeleccao = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        val cursor = when (getUriMatcher().match(uri)) {
            URI_EQUIPA -> TabelaBDEquipa(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_JOGADOR -> TabelaBDJogador(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_LOCALIDADE -> TabelaBDLocalidade(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_TREINADOR -> TabelaBDJogador(db).query(colunas, selection, argsSeleccao, null, null, sortOrder)
            URI_EQUIPA_ESPECIFICA -> TabelaBDEquipa(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_JOGADOR_ESPECIFICO -> TabelaBDJogador(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_LOCALIDADE_ESPECIFICO -> TabelaBDLocalidade(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            URI_TREINADOR_ESPECIFICO -> TabelaBDTreinador(db).query(colunas, "${BaseColumns._ID}=?", arrayOf("${id}"), null, null, null)
            else -> null
        }

        db.close()

        return cursor
    }

    override fun getType(uri: Uri): String? =
        when (getUriMatcher().match(uri)) {
            URI_EQUIPA -> "$MULTIPLOS_REGISTOS/${TabelaBDEquipa.NOME_TABELA}"
            URI_JOGADOR -> "$MULTIPLOS_REGISTOS/${TabelaBDEquipa.NOME_TABELA}"
            URI_LOCALIDADE -> "$MULTIPLOS_REGISTOS/${TabelaBDJogador.NOME_TABELA}"
            URI_TREINADOR -> "$MULTIPLOS_REGISTOS/${TabelaBDJogador.NOME_TABELA}"
            URI_EQUIPA_ESPECIFICA -> "$UNICO_REGISTO/${TabelaBDLocalidade.NOME_TABELA}"
            URI_JOGADOR_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDLocalidade.NOME_TABELA}"
            URI_LOCALIDADE_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDTreinador.NOME_TABELA}"
            URI_TREINADOR_ESPECIFICO -> "$UNICO_REGISTO/${TabelaBDTreinador.NOME_TABELA}"

            else -> null
        }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_EQUIPA -> TabelaBDEquipa(db).insert(values)
            URI_JOGADOR -> TabelaBDJogador(db).insert(values)
            URI_LOCALIDADE -> TabelaBDLocalidade(db).insert(values)
            URI_TREINADOR -> TabelaBDTreinador(db).insert(values)
            else -> -1
        }

        db.close()

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, "$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosApagados = when (getUriMatcher().match(uri)) {
            URI_EQUIPA_ESPECIFICA -> TabelaBDEquipa(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_JOGADOR_ESPECIFICO -> TabelaBDJogador(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LOCALIDADE_ESPECIFICO-> TabelaBDLocalidade(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_TREINADOR_ESPECIFICO -> TabelaBDTreinador(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosApagados
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        requireNotNull(values)

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        val registosAlterados = when (getUriMatcher().match(uri)) {
            URI_EQUIPA_ESPECIFICA -> TabelaBDEquipa(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_JOGADOR_ESPECIFICO -> TabelaBDJogador(db).update(values,"${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LOCALIDADE_ESPECIFICO -> TabelaBDLocalidade(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_TREINADOR_ESPECIFICO -> TabelaBDTreinador(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

        db.close()

        return registosAlterados
    }

    companion object {
        const val AUTHORITY = "pt.ipg.trabfinal2022progavanc"

        const val URI_EQUIPA = 100
        const val URI_EQUIPA_ESPECIFICA = 101
        const val URI_JOGADOR = 200
        const val URI_JOGADOR_ESPECIFICO = 201
        const val URI_LOCALIDADE = 300
        const val URI_LOCALIDADE_ESPECIFICO = 301
        const val URI_TREINADOR = 400
        const val URI_TREINADOR_ESPECIFICO = 401

        const val UNICO_REGISTO = "vnd.android.cursor.item"
        const val MULTIPLOS_REGISTOS = "vnd.android.cursor.dir"

        private val ENDEREÇO_BASE = Uri.parse(("content://$AUTHORITY"))
        val ENDERECO_JOGADORES = Uri.withAppendedPath(ENDEREÇO_BASE, TabelaBDJogador.NOME_TABELA)
        val ENDERECO_TREINADOR = Uri.withAppendedPath(ENDEREÇO_BASE, TabelaBDTreinador.NOME_TABELA)
        val ENDERECO_EQUIPA = Uri.withAppendedPath(ENDEREÇO_BASE, TabelaBDEquipa.NOME_TABELA)
        val ENDERECO_LOCALIDADE = Uri.withAppendedPath(ENDEREÇO_BASE, TabelaBDLocalidade.NOME_TABELA)

        fun getUriMatcher() : UriMatcher {
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, TabelaBDEquipa.NOME_TABELA, URI_EQUIPA)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDEquipa.NOME_TABELA}/#", URI_EQUIPA_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, TabelaBDJogador.NOME_TABELA, URI_JOGADOR)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDJogador.NOME_TABELA}/#", URI_JOGADOR_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, TabelaBDLocalidade.NOME_TABELA, URI_LOCALIDADE)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDLocalidade.NOME_TABELA}/#", URI_LOCALIDADE_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, TabelaBDTreinador.NOME_TABELA, URI_TREINADOR)
            uriMatcher.addURI(AUTHORITY, "${TabelaBDTreinador.NOME_TABELA}/#", URI_TREINADOR_ESPECIFICO)

            return uriMatcher
        }
    }
}