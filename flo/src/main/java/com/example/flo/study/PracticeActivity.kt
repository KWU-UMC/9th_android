package com.example.flo.study

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 데이터베이스 작업은 I/O 작업에 속하고, CPU 계산보다 시간이 훨씬 오래 걸린다.
 * 안드로이드는 메인 쓰레드에서 데이터베이스 접근을 금지한다.
 */
class PracticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tutorialStorage = TutorialStorage(this)
        if(!tutorialStorage.isTutorialCompleted()) {
            showTutorialScreen()
            tutorialStorage.setTutorialCompleted()
        }

        // 데이터베이스 인스턴스 가져오기
        val database = ProductDatabase.getInstance(this)
        val productDao = database.productDao()
        val categoryDao = database.categoryDao()

        // DB 작업 → ANR 문제 고려하여 UI 스레드가 아닌 백그라운드에서 처리 → 코루틴 사용
        CoroutineScope(Dispatchers.IO).launch {

            // Category Table에 데이터 삽입
            categoryDao.insertCategory(CategoryEntity(name = "Shoes"))
            categoryDao.insertCategory(CategoryEntity(name = "Socks"))
            categoryDao.insertCategory(CategoryEntity(name = "T-shirt"))

            // Product Table에 데이터 삽입
            productDao.insertProduct(ProductEntity(
                name = "Air Jordan",
                content = "There is only one way to celebrate...",
                image = null,
                price = 239,
                liking = true,
                categoryId = 1
            ))
            productDao.insertProduct(ProductEntity(
                name = "Air Force",
                content = "We have amazing Air Force shoes",
                image = null,
                price = 400,
                liking = false,
                categoryId = 1
            ))
            productDao.insertProduct(ProductEntity(
                name = "Yellow Socks",
                content = "Yello socks description",
                image = null,
                price = 40,
                liking = false,
                categoryId = 2
            ))
            productDao.insertProduct(ProductEntity(
                name = "Jacket",
                content = "Black and M size with leather",
                image = null,
                price = 1000,
                liking = true,
                categoryId = 3
            ))

            val allProducts = productDao.getAllProducts()
            withContext(Dispatchers.Main) {
                // UI 업데이트는 메인 쓰레드에서
                Log.e("DB", "" + allProducts)
            }

        }
    }

    private fun showTutorialScreen() {

    }
}