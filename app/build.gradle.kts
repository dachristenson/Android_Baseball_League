val kotlin_version = "1.9.0"
val app_compat_version = "1.6.1"
val nav_version = "2.7.6"
val room_version = "2.6.1"
val material_version = "1.11.0"
val retrofit_version = "2.9.0"
val abl_client_version = "1.1.1"
val paging_version = "3.2.1"

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.abl"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.abl"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:$app_compat_version")
    implementation("com.google.android.material:material:material_version")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("com.google.android.material:material:$material_version")
    implementation("androidx.room:room-common:$room_version")
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("dev.mfazio:abl-api-client:$abl_client_version")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("androidx.room:room-paging:2.6.1")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.0")
    platform("com.google.firebase:firebase-bom:32.7.1")
    kapt("androidx.room:room-compiler:$room_version")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}