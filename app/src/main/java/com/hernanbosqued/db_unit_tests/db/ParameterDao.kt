package com.hernanbosqued.db_unit_tests.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParameterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parameter: ParameterEntity)

    @Query("SELECT * FROM parameter WHERE id = (SELECT MAX(id) FROM parameter)")
    suspend fun selectLast(): ParameterEntity?

    @Query("DELETE FROM parameter WHERE id = (SELECT MAX(id) FROM parameter)")
    suspend fun deleteLast()

    @Query("SELECT * FROM parameter")
    suspend fun selectAll(): List<ParameterEntity>

    @Query("SELECT count(*) FROM parameter")
    suspend fun countRecords(): Int
}