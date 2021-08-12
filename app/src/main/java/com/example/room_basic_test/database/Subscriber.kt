package com.example.room_basic_test.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 데이터베이스의 tableName과 컬럼 설정
@Entity(tableName = "subscriber_test_table1")
data class Subscriber(
    // 컬럼명 및 특성 정의
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    val id : Int,

    @ColumnInfo(name = "subscriber_name")
    val name : String,

    @ColumnInfo(name = "subscriber_email")
    val email : String
)