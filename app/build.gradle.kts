plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hadeer.photogalleryapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.hadeer.photogalleryapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_URL", "\"https://api.pexels.com/v1/\"")
            buildConfigField("String" , "AUTH_TOKEN", "\"0TXuK4iuPRHZe8axXzqbNWmMNFcRYuoO1MLvlKYaL4qYC0PbQASsBElV\"")
            android.buildFeatures.buildConfig = true
        }
        release {
            buildConfigField("String", "API_URL", "\"https://api.pexels.com/v1/\"")
            buildConfigField("String" , "AUTH_TOKEN", "\"0TXuK4iuPRHZe8axXzqbNWmMNFcRYuoO1MLvlKYaL4qYC0PbQASsBElV\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        viewBinding = true
    }
}

buildscript{
    repositories{
        google()
        mavenCentral()
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
    //link with data module
    implementation(project(":data"))
    implementation(project(":domain"))

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Dagger Hilt dependencies
    implementation(libs.hilt.android)
    androidTestImplementation(libs.androidx.core.testing)
    kapt(libs.hilt.compiler)
    //OkHttp
    implementation(libs.okhttp)
    //Retrofit
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson.v290)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    //Live Data ViewModel
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //for Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    testImplementation(kotlin("test"))


    //TEST
    // In your app-level build.gradle
    testImplementation(libs.androidx.core.testing) // For InstantTaskExecutorRule
    testImplementation(libs.kotlinx.coroutines.test.v173) // For runTest
    testImplementation(libs.kotlinx.coroutines.test.v16)
}