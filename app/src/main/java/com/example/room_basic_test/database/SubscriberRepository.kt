package com.example.room_basic_test.database

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {
    val subscribers = subscriberDAO.getSubscriber()

    suspend fun insert(subscriber: Subscriber) : Long{
        return subscriberDAO.insert(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) : Int{
        return subscriberDAO.delete(subscriber)
    }

    suspend fun update(subscriber: Subscriber) : Int{
        return subscriberDAO.update(subscriber)
    }

    suspend fun clear() : Int{
        return subscriberDAO.clear()
    }
}