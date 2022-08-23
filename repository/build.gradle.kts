import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id ("com.android.library")
    kotlin ("android")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies{
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    implementation("com.google.code.gson:gson:2.9.1")

}