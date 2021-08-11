package com.example.room_basic_test.database

// 각 기능들이 코루틴으로 실행될 수 있게 suspend 함수로 정의
class SubscribeRepository(private val dao : SubscriberDAO) {
    // 모든 데이터 가져오기
    val subscriber = dao.getAllSubscribers()

    // 새로운 데이터 넣기
    suspend fun insert(subscriber : Subscriber){
        dao.insertSubscriber(subscriber)
    }

    // 선택한 데이터 삭제하기
    suspend fun delete(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    // 모든 데이터 삭제하기
    suspend fun deleteAll(){
        dao.deleteAll()
    }

    // 선택한 데이터 업데이트하기
    suspend fun update(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }
}