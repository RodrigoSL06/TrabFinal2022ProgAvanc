package pt.ipg.trabfinal2022progavanc

import android.database.sqlite.SQLiteDatabase
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


}