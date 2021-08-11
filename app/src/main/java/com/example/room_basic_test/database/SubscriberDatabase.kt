package com.example.room_basic_test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 어떤 데이터를 참고하여 DB를 만들지, 버전은 뭐로할지 정의
@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase(){
    abstract val subscriberDAO : SubscriberDAO

    companion object{
        // Volatile: 공유되는 변수에서 사용되면 주로 멀티 스레드 처리에 사용된다
        // 스레드에서 엑세스 할 때마다 반드시 공유 메모리상의 변수의 값과 스레드 값을 일치시킨다
        // 즉, 다른 스레드에서 참조할 수 있는 변수임
        @Volatile
        private var INSTANCE : SubscriberDatabase? = null
        // 여기 아랫부분은 모든 Room 라이브러리를 쓸 때 공통적으로 쓰는 부분이다
        // 다른 프로젝트에서 쓸 때는 클래스 이름(SubscriberDatabase::class.java)과
        // DB 이름만 바꿔서 사용하면된다
        fun getInstance(context: Context) : SubscriberDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}