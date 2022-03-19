package com.hernanbosqued.db_unit_tests.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parameter")
data class ParameterEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "number") val number: Int = 0

)

fun List<ParameterEntity>.toDomain() = this.map { it.toDomain() }
fun ParameterEntity.toDomain() = this.number