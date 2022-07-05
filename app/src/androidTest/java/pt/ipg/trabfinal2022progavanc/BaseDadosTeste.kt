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

    private fun insereTreinador(db: SQLiteDatabase, treinador: Treinador) {
        treinador.id = TabelaBDTreinador(db).insert(treinador.toContentValues())
        assertNotEquals(-1, treinador.id)
    }

    private fun insereJogador(db: SQLiteDatabase, jogador : Jogador) {
        jogador.id = TabelaBDJogador(db).insert(jogador.toContentValues())
        assertNotEquals(-1, jogador.id)
    }

    private fun insereLocalidade(db: SQLiteDatabase, localidade : Localidade) {
        localidade.id = TabelaBDLocalidade(db).insert(localidade.toContentValues())
        assertNotEquals(-1, localidade.id)
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

    @Test
    fun consegueInserirLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("Aveiro")
        insereLocalidade(db, localidade)

        db.close()
    }

    @Test
    fun consegueInserirEquipa() {
        val db = getWritableDatabase()

        val localidade = Localidade("Barcelos")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Gil Vicente", localidade)
        insereEquipa(db, equipa)


        db.close()
    }

    @Test
    fun consegueInserirTreinador() {
        val db = getWritableDatabase()

        val localidade = Localidade("Lisboa")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Sporting", localidade )
        insereEquipa(db, equipa)

        val treinador = Treinador("Rui", equipa.id, equipa.nomeEquipa, "936583413")
        treinador.id = TabelaBDTreinador(db).insert(treinador.toContentValues())

        assertNotEquals(-1, treinador.id)

        db.close()
    }


    @Test
    fun consegueInserirJogador() {
        val db = getWritableDatabase()

        val localidade = Localidade("Lisboa")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Sporting", localidade.id )
        insereEquipa(db, equipa)

        val jogador = Jogador( "Ruben", "7", equipa.id, equipa.nome,"12/2/1993", "924563456")
        insereJogador(db, jogador)

        db.close()
    }

    @Test
    fun consegueAlterarLocalidade() {

        val db = getWritableDatabase()

        val localidade = Localidade("TESTE")
        insereLocalidade(db, localidade)

        localidade.nome = "Guarda"
        val registosAlterados = TabelaBDLocalidade(db).update(
            localidade  .toContentValues(),
            "${TabelaBDLocalidade.CAMPO_ID}=?",
            arrayOf("${localidade.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueAlterarEquipa() {

        val db = getWritableDatabase()

        val localidadeGuarda = Localidade("Guarda")
        insereLocalidade(db, localidadeGuarda)

        val localidadeLisboa = Localidade("Lisboa")
        insereLocalidade(db, localidadeLisboa)

        val equipa = Equipa("TESTE", localidadeGuarda.id)
        insereEquipa(db, equipa)

        equipa.nome= "A rapariga no comboio"
        equipa.localidade = localidadeLisboa.id

        val registosAlterados = TabelaBDEquipa(db).update(
            equipa.toContentValues(),
            "${TabelaBDEquipa.CAMPO_ID}=?",
            arrayOf("${equipa.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }


    @Test
    fun consegueAlterarTreinador() {

        val db = getWritableDatabase()

        val localidadeGuarda = Localidade("Guarda")
        insereLocalidade(db, localidadeGuarda)

        val localidadeLisboa = Localidade("Lisboa")
        insereLocalidade(db, localidadeLisboa)

        val equipaSporting = Equipa("Sporting", localidadeLisboa.id, )
        insereEquipa(db, equipaSporting)

        val equipaBenfica = Equipa("Benfica", localidadeLisboa.id)
        insereEquipa(db, equipaBenfica)

        val treinador = Treinador("Manel", equipaSporting.id, equipaSporting.nome ,"12/07/1998", "931313313")
        insereTreinador(db, treinador)

        treinador.nome = "A rapariga no comboio"
        treinador.idEquipa = equipaBenfica.id
        treinador.nomeEquipa = equipaBenfica.nome
        treinador.dataNascimento = "01/01/1987"
        treinador.telemovel = "925668090"

        val registosAlterados = TabelaBDTreinador(db).update(
            treinador.toContentValues(),
            "${TabelaBDTreinador.CAMPO_ID}=?",
            arrayOf("${treinador.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }


    fun consegueAlterarJogador() {

        val db = getWritableDatabase()

        val localidadeGuarda = Localidade("Guarda")
        insereLocalidade(db, localidadeGuarda)

        val localidadeLisboa = Localidade("Lisboa")
        insereLocalidade(db, localidadeLisboa)

        val equipaPorto = Equipa("Porto", localidadeLisboa.id)
        insereEquipa(db, equipaPorto)

        val equipaBraga = Equipa("Braga", localidadeLisboa.id)
        insereEquipa(db, equipaBraga)

        val jogador = Jogador("Ze", "4", equipaPorto.id, "Sporting","23/07/1990", "965874514")
        insereJogador(db, jogador)

        jogador.nome = "A rapariga no comboio"
        jogador.ncamisola = "11"
        jogador.idEquipa = equipaBraga.id
        jogador.data_nascimento = "01/01/1987"
        jogador.telemovel = "925668090"

        val registosAlterados = TabelaBDJogador(db).update(
            jogador.toContentValues(),
            "${TabelaBDJogador.CAMPO_ID}=?",
            arrayOf("${jogador.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueEliminarLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("TESTE")
        insereLocalidade(db, localidade)

        val registosEliminados = TabelaBDLocalidade(db).delete(
            "${TabelaBDLocalidade.CAMPO_ID}=?",
            arrayOf("${localidade.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarEquipa() {
        val db = getWritableDatabase()

        val localidade = Localidade("Viseu")
        insereLocalidade(db, localidade)

        val equipa = Equipa("TESTE", localidade.id)
        insereEquipa(db, equipa)

        val registosEliminados = TabelaBDEquipa(db).delete(
            "${TabelaBDEquipa.CAMPO_ID}=?",
            arrayOf("${equipa.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarTreinador() {
        val db = getWritableDatabase()

        val localidade = Localidade("Viseu")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Boavista", localidade.id)
        insereEquipa(db, equipa)

        val treinador = Treinador("TESTE", equipa.id, "13/02/1991","925887090")
        insereTreinador(db, treinador)

        val registosEliminados = TabelaBDTreinador(db).delete(
            "${TabelaBDTreinador.CAMPO_ID}=?",
            arrayOf("${treinador.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarJogador() {
        val db = getWritableDatabase()

        val localidade = Localidade("Viseu")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Boavista", localidade.id)
        insereEquipa(db, equipa)

        val jogador = Jogador("TESTE","4", equipa.id, "Boavista", "14/03/1992", "924556769")
        insereJogador(db, jogador)

        val registosEliminados = TabelaBDJogador(db).delete(
            "${TabelaBDJogador.CAMPO_ID}=?",
            arrayOf("${jogador.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("Aventura")
        insereLocalidade(db, localidade)

        val cursor = TabelaBDLocalidade(db).query(
            TabelaBDLocalidade.TODAS_COLUNAS,
            "${TabelaBDLocalidade.CAMPO_ID}=?",
            arrayOf("${localidade.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val localidadeBD = Localidade.fromCursor(cursor)
        assertEquals(localidade, localidadeBD)

        db.close()
    }


    @Test
    fun consegueLerEquipa() {
        val db = getWritableDatabase()

        val localidade = Localidade("Porto")
        insereLocalidade(db, localidade)

        val equipa = Equipa("FCPorto", localidade)
        insereEquipa(db, equipa)

        val cursor = TabelaBDEquipa(db).query(
            TabelaBDEquipa.TODAS_COLUNAS,
            "${TabelaBDEquipa.CAMPO_ID}=?",
            arrayOf("${equipa.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val equipaBD = Equipa.fromCursor(cursor)
        assertEquals(equipa, equipaBD)

        db.close()
    }


    @Test
    fun consegueLerTreinador() {
        val db = getWritableDatabase()

        val localidade = Localidade("Viseu")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Tondela", localidade.id)
        insereEquipa(db, equipa)

        val treinador = Treinador("Antonio", equipa.id, "15/04/1998","914556768")
        insereTreinador(db, treinador)

        val cursor = TabelaBDTreinador(db).query(
            TabelaBDTreinador.TODAS_COLUNAS,
            "${TabelaBDTreinador.CAMPO_ID}=?",
            arrayOf("${treinador.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val treinadorBD = Treinador.fromCursor(cursor)
        assertEquals(treinador, treinadorBD)

        db.close()
    }

    @Test
    fun consegueLerJogador() {
        val db = getWritableDatabase()

        val localidade = Localidade("Viseu")
        insereLocalidade(db, localidade)

        val equipa = Equipa("Tondela", localidade.id)
        insereEquipa(db, equipa)

        val jogador = Jogador("Pedro", "15", equipa.id, "Tondela","11/04/1996", "914500068")
        insereJogador(db, jogador)

        val cursor = TabelaBDJogador(db).query(
            TabelaBDJogador.TODAS_COLUNAS,
            "${TabelaBDJogador.CAMPO_ID}=?",
            arrayOf("${jogador.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val jogadorBD = Jogador.fromCursor(cursor)
        assertEquals(jogador, jogadorBD)

        db.close()
    }
}
