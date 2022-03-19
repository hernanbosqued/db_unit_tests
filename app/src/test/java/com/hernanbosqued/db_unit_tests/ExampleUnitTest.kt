package com.hernanbosqued.db_unit_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hernanbosqued.db_unit_tests.db.AppDatabase
import com.hernanbosqued.db_unit_tests.db.ParameterDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest{
    private lateinit var database: AppDatabase
    private lateinit var parameterDao: ParameterDao
    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java).allowMainThreadQueries().build()
        parameterDao = database.parameterDao()
        viewModel = MainViewModel(ParameterStorageImpl(database))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `add 4 subtract 1`() {
        runTest {
            viewModel.add()
            viewModel.add()
            viewModel.add()
            viewModel.add()
            viewModel.subtract()
            val records = parameterDao.countRecords()
            assertEquals(3,records)
        }
    }
}