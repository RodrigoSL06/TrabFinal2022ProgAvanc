package pt.ipg.trabfinal2022progavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


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

    private fun insereLocalidade(db: SQLiteDatabase, localidade : Localidade) {
        localidade.id = TabelaBDLocalidade(db).insert(localidade.toContentValues())
        assertNotEquals(-1, localidade.id)
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

        insereLocalidade(db, Localidade("Porto"))


        db.close()
    }

    @Test
    fun consegueInserirEquipa() {
        val db = getWritableDatabase()

        val localidade = Localidade("Porto")
        insereLocalidade(db, localidade)

        val localidade2 = Localidade("Guarda")
        insereLocalidade(db, localidade2)

        val localidade3 = Localidade("Lisboa")
        insereLocalidade(db, localidade3)

        val equipa = Equipa("Porto",  localidade)
        equipa.id = TabelaBDEquipa(db).insert(equipa.toContentValues())

        val equipa2 = Equipa("NDS",  localidade)
        equipa2.id = TabelaBDEquipa(db).insert(equipa2.toContentValues())

        val equipa3 = Equipa("Sporting", localidade)
        equipa3.id = TabelaBDEquipa(db).insert(equipa3.toContentValues())


        assertNotEquals(-1, equipa.id)
        assertNotEquals(-1, equipa2.id)
        assertNotEquals(-1, equipa3.id)

        db.close()
    }

    @Test
    fun consegueInserirTreinador() {
        val db = getWritableDatabase()

        val treinador = Treinador("Luis", "12/08/1998", "913131311")
        treinador.id = TabelaBDTreinador(db).insert(treinador.toContentValues())

        assertNotEquals(-1, treinador.id)

        db.close()
    }


    @Test
    fun consegueInserirJogador() {
        val db = getWritableDatabase()

        val jogador = Jogador( "Ruben", "7","12/2/1993", "924563456")
        insereJogador(db, jogador)

        db.close()
    }

    @Test
    fun consegueAlterarLocalidade() {

        val db = getWritableDatabase()

        val localidade = Localidade("TESTE")
        insereLocalidade(db, localidade)

        localidade.nomeLocalidade = "Guarda"

        val registosAlterados = TabelaBDLocalidade(db).update(
            localidade  .toContentValues(),
            "${BaseColumns._ID}=?",
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

        val equipa = Equipa("Maritimo", localidadeGuarda)
        insereEquipa(db, equipa)

        equipa.nomeEquipa= "Gil Vicente"
        equipa.localidade = localidadeLisboa

        val registosAlterados = TabelaBDEquipa(db).update(
            equipa.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${equipa.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }


    @Test
    fun consegueAlterarTreinador() {

        val db = getWritableDatabase()

        val treinador = Treinador("Manel", "14/07/1981" ,"925887471")
        insereTreinador(db, treinador)

        treinador.nome = "Mateus"
        treinador.dataNascimento = "01/01/1987"
        treinador.telemovel = "925668090"

        val registosAlterados = TabelaBDTreinador(db).update(
            treinador.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${treinador.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueAlterarJogador() {

        val db = getWritableDatabase()

        val jogador = Jogador("Zé", "4", "13/03/1980","912345676")
        insereJogador(db, jogador)

        jogador.nome = "Tone"
        jogador.ncamisola = "4"
        jogador.dataNascimento = "01/02/1989"
        jogador.telemovel = "934563212"

        val registosAlterados = TabelaBDJogador(db).update(
            jogador  .toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${jogador.id}")
        )

        assertEquals(1, registosAlterados)

        db.close()

    }

    @Test
    fun consegueEliminarLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("Guarda")
        insereLocalidade(db, localidade)

        val registosEliminados = TabelaBDLocalidade(db).delete(
            "${BaseColumns._ID}=?",
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

        val equipa = Equipa("Portalegre", localidade)
        insereEquipa(db, equipa)

        val registosEliminados = TabelaBDEquipa(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${equipa.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarTreinador() {
        val db = getWritableDatabase()

        val treinador = Treinador("TESTE",  "13/02/1991","925887090")
        insereTreinador(db, treinador)

        val registosEliminados = TabelaBDTreinador(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${treinador.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarJogador() {
        val db = getWritableDatabase()

        val jogador = Jogador("TESTE","4","Boavista", "14/03/1992")
        insereJogador(db, jogador)

        val registosEliminados = TabelaBDJogador(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${jogador.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerLocalidade() {
        val db = getWritableDatabase()

        val localidade = Localidade("Leiria")
        insereLocalidade(db, localidade)

        val cursor = TabelaBDLocalidade(db).query(
            TabelaBDLocalidade.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
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

        val treinador = Treinador("Antonio", "15/04/1998","914556768")
        insereTreinador(db, treinador)

        val cursor = TabelaBDTreinador(db).query(
            TabelaBDTreinador.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
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

        val jogador = Jogador("Pedro", "15",  "Tondela", "11/04/1996")
        insereJogador(db, jogador)

        val cursor = TabelaBDJogador(db).query(
            TabelaBDJogador.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
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
