plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.example.codingchallenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.codingchallenge"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //retrofit connection
    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //RxJava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)

    /* //RxLifecycle
     implementation("com.trello.rxlifecycle4:rxlifecycle:4.0.2")
     implementation("com.trello.rxlifecycle4:rxlifecycle-android:4.0.2")
     implementation("com.trello.rxlifecycle2:rxlifecycle-components:4.0.2")
 */

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    // LiveData
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    //Gson for parsing json
    implementation(libs.gson)

    //hilt
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.compiler)

    //Glide for image loading
    implementation(libs.glide)
}