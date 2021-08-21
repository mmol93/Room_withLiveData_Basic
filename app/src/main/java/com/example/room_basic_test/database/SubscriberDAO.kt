package com.example.room_basic_test.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscriber: Subscriber) : Long

    @Delete
    suspend fun delete(subscriber: Subscriber) : Int

    @Query("DELETE FROM subscribe_data_table")
    suspend fun clear() : Int

    @Update
    suspend fun update(subscriber: Subscriber) : Int

    @Query("SELECT * FROM subscribe_data_table")
    fun getSubscriber() : Flow<List<Subscriber>>
}