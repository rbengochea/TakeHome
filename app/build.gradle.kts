plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.amano"
        minSdk = 29
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

    }

    buildFeatures{
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile(
                "proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation (project(":repository"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    testImplementation("junit:junit:5.7.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")


    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0-alpha01")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0-alpha01")
}