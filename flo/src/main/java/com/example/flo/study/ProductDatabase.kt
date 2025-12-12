package com.example.flo.study

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Entity와 DAO를 연결하고, 데이터베이스 객체를 관리한다.
 * 이 클래스는 보통 싱글톤(Singleton) 패턴으로 구현하여 앱 전체에서 단 하나의 데이터베이스 인스턴스만 존재하도록 생성한다.
 * @ProductDatabase: entites에는 사용할 모든 Entity(Table)을 나열한다.
 * version은 현재 데이터베이스의 상태로, DB 구조가 변경될 때마다 해당 숫자를 올려줌으로써 데이터베이스가 바뀌었음을 Room이 인지한다
 * abstract class ProductDatabase → Room에게 구현을 맡기기 위해(구체적인 자식 클래스 생성) 추상 클래스로 선언
 * abstract fun productDao(): ProductDao → 이 메서드 역시 Room이 컴파일 시간에 생성하는 구체적인 ProductDatabase 구현체 클래스 내에서 실제로 구현된다. RoomDatabase를 상속하는 클래스는 반드시 DAO를 반환하는 추상 함수를 가지고 있어야 한다(규칙).
 * @Volatile → 메모리 가시성을 보장
 * synchronized(this) → 한 번에 하나의 스레드만 이 블록 내부의 코드를 실행하도록 보장하여 여러 스레드가 동시에 데이터베이스를 초기화하려고 시도하는 것을 막는다. INSTANCE가 null일 때, 두 스레드가 거의 동시에 진입하여 각각 Room.databaseBuilder().build()를 두 번 실행하여 두 개의 데이터베이스 인스턴스가 생성되는 상황(Race Condition)을 방지한다.
 * context.applicationContext → 메모리 누수 위험: Activity Context를 데이터베이스와 같이 수명이 긴 객체에 전달하여 보관하면, Activity가 종료되어도 가비지 컬렉션(GC)이 컨텍스트를 회수하지 못하여 메모리 누수가 발생할 수 있다. applicationContext는 애플리케이션의 전체 수명 주기와 함께하며, 이는 데이터베이스(ProductDatabase)의 수명 주기와 일치한다. 따라서 이를 사용하면 메모리 누수 위험 없이 안전하게 데이터베이스 인스턴스를 만들 수 있다.
 */
@Database(entities = [ProductEntity::class, CategoryEntity::class], version = 1)
abstract class ProductDatabase: RoomDatabase() { // 클래스 구현체 자동 생성

    abstract fun productDao(): ProductDao // 메소드 구현체 자동 생성
    abstract fun categoryDao(): CategoryDao // 메소드 구현체 자동 생성

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null
        fun getInstance(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = ProductDatabase::class.java,
                    name = DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
        const val DATABASE_NAME = "database_product"
    }
}