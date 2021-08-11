package com.example.room_basic_test.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// 데이터베이스의 insert, delete, update, Query 등 조작 정의
interface SubscriberDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_test_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM subscriber_test_table")
    // 코루틴 Flow
    fun getAllSubscribers() : Flow<List<Subscriber>>
}