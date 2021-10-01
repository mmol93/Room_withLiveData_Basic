package com.example.room_basic_test.database

// DAO 인터페이스를 생성자로 가져온다 -> DAO에 있는 요소를 사용할 예정
// 여기선 SubscriberDAO에서 정의한 함수를 실행하고 반환을 해준다
class SubscriberRepository(private val dao : SubscriberDAO) {
    val subscribers = dao.getAllSubscriber()

    // Room 데이터베이스는 데이터를 자동으로 liveData로 처리하기 때문에
    // suspend fun을 사용한다
    suspend fun insert(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun clear(){
        dao.clearSubscribers()
    }
}