package com.sipl.fieldwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sipl.fieldwork.database.entity.Caste
import com.sipl.fieldwork.database.entity.Gender

@Dao
interface CasteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<Caste>)

    @Query("DELETE FROM gender")
    suspend fun deleteAllCastes()

    @Query("SELECT COUNT(*) FROM gender WHERE is_active=1")
    suspend fun getRowCount(): Int
    @Transaction
    suspend fun insertInitialRecords(items: List<Caste>) {
        deleteAllCastes()
        insertAll(items)
    }

    @Query("SELECT * FROM gender WHERE is_active=1 ORDER BY id ASC")
    suspend fun getAllCastes(): LiveData<List<Caste>>

    @Query("SELECT * FROM gender WHERE id = :id AND is_active=1")
    suspend fun getCasteById(id: String): Caste

}