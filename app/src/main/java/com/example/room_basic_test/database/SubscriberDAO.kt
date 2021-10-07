package com.example.room_basic_test.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    // @Insert: 해당 정보(테이블, 컬러명 등)로 DB를 만든다
    // Room은 main 스레드에 있는 데이터 베이스에 접근할 수 없다
    // 그래서 suspend fun를 이용해서 background에서 작동하게 한다
    // OnConflictStrategy.REPLACE: 해당 인덱스에 이미 값이 있을 경우 replace 한다
    // Long 반환을 사욯하면 rowId를 반환받을 수 있다
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    // 구독자 데이터 업데이트
    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) : Int

    // 구독자 데이터 삭제
    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) : Int

    // 구독자 데이터 전부 삭제
    @Query("Delete FROM subscriber_data_table")
    suspend fun clearSubscribers()

    // 구독자 데이터 전부 읽기
    // Room2.2부터 코루틴의 FLOW 사용가능
    // FLOW 사용시 함수 호출은 메인 스레드에서, 데이터 처리는 백그라운드에서 가능하다
    // 호출을 메인 스레드에서 하기 때문에 그냥 fun
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscriber() : Flow<List<Subscriber>>
}