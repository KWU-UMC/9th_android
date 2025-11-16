package com.example.flo.study

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * @Insert, @Update, @Delete: Room이 object를 받아 자동으로 DB 작업을 수행하는 어노테이션. 덕부에 별도의 쿼리문 작성 필요 없음
 * @Query("SQL 쿼리문"): 복잡한 데이터 조회나 특정 조건에 맞는 데이터 조작을 위해 직접 SQL 쿼리를 작성
 */
@Dao
interface ProductDao {
    // 새 상품 등록
    @Insert
    fun insertProduct(product: ProductEntity)

    // 상품 정보 수정
    @Update
    fun updateProduct(product: ProductEntity)

    // 특정 상품 삭제
    @Delete
    fun deleteProduct(product: ProductEntity)

    // 모든 상품 조회
    @Query("SELECT * FROM ProductTable")
    fun getAllProducts(): List<ProductEntity>

    // 특정 카테고리의 상품 조회
    @Query("SELECT * FROM ProductTable WHERE categoryId = :categoryId")
    fun getProductsByCategory(categoryId: Int): List<ProductEntity>
}

@Dao
interface CategoryDao {
    // 새 카테고리 등록
    @Insert
    fun insertCategory(category: CategoryEntity)

    // 기존 카테고리 정보 수정
    @Update
    fun updateCategory(category: CategoryEntity)

    // 특정 카테고리 삭제
    @Delete
    fun deleteCategory(category: CategoryEntity)

    // 모든 카테고리 정보 조회
    @Query("SELECT * FROM CategoryTable")
    fun getAllCategories(): List<CategoryEntity>
}