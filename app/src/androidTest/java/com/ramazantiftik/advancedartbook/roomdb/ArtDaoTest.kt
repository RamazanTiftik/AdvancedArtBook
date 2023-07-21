package com.ramazantiftik.advancedartbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.ramazantiftik.advancedartbook.getOrAwaitValue
import com.ramazantiftik.advancedartbook.model.Art
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    lateinit var dao: ArtDao

    @Before
    fun setup(){

        /*
        database= Room.inMemoryDatabaseBuilder( // in ram memory / temple database
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()
         */

        hiltRule.inject()

        dao=database.artDao()

    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertArtTestting() = runBlockingTest {

        val exampleArt= Art("Mona Lisa", "Da Vinci", 1700, "test.com", 1)
        dao.insertArt(exampleArt)

        val list=dao.getAllArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)

    }

    @Test
    fun deleteArtTestting() = runBlockingTest{

        val exampleArt= Art("Mona Lisa", "Da Vinci", 1700, "test.com", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list=dao.getAllArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)

    }

}