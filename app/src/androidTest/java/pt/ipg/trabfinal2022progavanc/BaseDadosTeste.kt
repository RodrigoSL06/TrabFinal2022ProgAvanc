package pt.ipg.trabfinal2022progavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BaseDadosTeste {
    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDFutsalOpenHelper(appContext())
        return openHelper.writableDatabase
    }

    private fun insereEquipa(db: SQLiteDatabase, equipa : Equipa) {
        equipa.id = TabelaBDEquipa(db).insert(equipa.toContentValues())
        assertNotEquals(-1, equipa.id)
    }

    private fun insereTreinador(db: SQLiteDatabase, treinador : Treinador) {
        treinador.id = TabelaBDTreinador(db).insert(treinador.toContentValues())
        assertNotEquals(-1, treinador.id)
    }

    private fun insereJogador(db: SQLiteDatabase, jogador : Jogador) {
        jogador.id = TabelaBDEquipa(db).insert(jogador.toContentValues())
        assertNotEquals(-1, jogador.id)
    }


    @Test
    fun apagaBaseDados() {
        appContext().deleteDatabase(BDFutsalOpenHelper.NOME)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDFutsalOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    fun consegueInserirEquipa() {
        val db = getWritableDatabase()

        val equipa = Equipa(
            nome = "Sporting",
            localidade = "Lisboa",
            njogador = "11",
            treinador = "Ruben Amorim"
        )

        TabelaBDEquipa(db).insert(equipa.toContentValues())

        db.close()
    }

    fun consegueInserirTreinador() {
        val db = getWritableDatabase()

        val treinador = Treinador(
            nome = "Reuben Amorim",
            equipa = "Sporting",
            data_nascimento = Date(1980 - 1900, 1, 7),
            telemovel = "963656789"
        )

        TabelaBDTreinador(db).insert(treinador.toContentValues())

        db.close()
    }

    fun consegueInserirJogador() {
        val db = getWritableDatabase()

        val jogador = Jogador(
            nome = "Pedro Gonçalves",
            ncamisola = "7",
            equipa = "Sporting",
            data_nascimento = Date(1991 - 1900, 4, 11),
            telemovel = "932544879"
        )

        TabelaBDJogador(db).insert(jogador.toContentValues())

        db.close()
    }

    fun consegueInserirLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade(
            nome = "Sporting"
        )

        TabelaBDLocalidade(db).insert(localidade.toContentValues())

        db.close()
    }

    fun consegueAlterarEquipa() {

        val db = getWritableDatabase()

        val equipa = Equipa(
            nome = "Braga",
            localidade = "Braga",
            njogador = "11",
            treinador = "Carlos Carvalhal"
        )

            equipa.nome = "Tondela"
            equipa.localidade = "Viseu"
            equipa.njogador = "11"
            equipa.treinador ="Nuno Campos"

        val registosAlterados = TabelaBDEquipa(db).update(
            equipa.toContentValues(),
            "${BaseColumns._ID}= ?",
            arrayOf("${equipa.id}"))

            assertEquals(1,registosAlterados)

            db.close()

    }

    fun consegueAlterarTreinador() {

        val db = getWritableDatabase()

        val treinador = Treinador(
            nome = "Pepa",
            equipa = "Guimaraes",
            data_nascimento = Date(1975 - 1900, 4, 11),
            telemovel = "912587449"
        )

        treinador.nome = "Nuno Oliveira"
        treinador.equipa = "Belenenses"
        treinador.data_nascimento = Date(1979 - 1900, 4, 11)
        treinador.telemovel = "913587456"

        val registosAlterados = TabelaBDTreinador(db).update(
            treinador.toContentValues(),
            "${BaseColumns._ID}= ?",
            arrayOf("${treinador.id}"))

        assertEquals(1,registosAlterados)

        db.close()

    }

    fun consegueAlterarJogador() {

        val db = getWritableDatabase()

        val jogador = Jogador(
            nome = "Andre",
            ncamisola = "10",
            equipa = "Guimaraes",
            data_nascimento = Date(1993 - 1900, 5, 11),
            telemovel = "912580047"
        )

        jogador.nome = "Varela"
        jogador.ncamisola = "19"
        jogador.equipa = "Belenenses"
        jogador.data_nascimento = Date(1994 - 1900, 10, 11)
        jogador.telemovel = "913544450"

        val registosAlterados = TabelaBDTreinador(db).update(
            jogador.toContentValues(),
            "${BaseColumns._ID}= ?",
            arrayOf("${jogador.id}"))

        assertEquals(1,registosAlterados)

        db.close()

    }

    fun consegueAlterarLocalidade() {

        val db = getWritableDatabase()

        val localidade = Localidade(
            nome = "Lisboa",

        )

        localidade.nome = "Guimaraes"

        val registosAlterados = TabelaBDTreinador(db).update(
            localidade.toContentValues(),
            "${BaseColumns._ID}= ?",
            arrayOf("${localidade.id}"))

        assertEquals(1,registosAlterados)

        db.close()

    }
}