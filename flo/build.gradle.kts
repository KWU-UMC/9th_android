import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Kotlin serialization plugin for type safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.flo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.flo"
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
        val kakaoNativeAppKey = properties.getProperty("KAKAO_NATIVE_APP_KEY") // local.properties에서 카카오 네이티브 앱 키 불러오기
        addManifestPlaceholders(mapOf("kakaoScheme" to "kakao${kakaoNativeAppKey}")) // 카카오 네이티브 앱 키를 manifest에 값 주입
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${kakaoNativeAppKey}\"") // 카카오 네이티브 앱 키를 BuildConfig 클래스에 주입
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Views/Fragments integration
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    // JSON serialization library, works with the Kotlin serialization plugin
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.dotsindicator)
    implementation(libs.circleindicator)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.gson)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.retrofit) // retrofit
    implementation(libs.converter.gson) // gson(json parser)
    implementation(libs.logging.interceptor) // 로그 출력
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    kapt(libs.androidx.room.compiler)
    implementation(libs.v2.user) // 카카오 로그인 API
    implementation(libs.glide) // glide
}