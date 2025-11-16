package com.example.flo.study

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity(tableName = "")은 해당 data class가 데이터베이스의 테이블임을 RoomDB에게 알려준다. tableName 속성으로 테이블의 이름을 지정할 수 있다.
 * @PrimaryKey(autoGenerate = true)은 이 속성이 테이블의 기본 키(Primary Key)이며, 새로운 데이터가 추가될 때마다 값이 자동으로 증가하도록 설정한다.
 */
@Entity(tableName = "ProductTable")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val content: String,
    val image: Int? = null,
    val price : Int,
    val liking: Boolean,
    val categoryId: Int, // Reference Key(=Foreign Key)
)

@Entity(tableName = "CategoryTable")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)