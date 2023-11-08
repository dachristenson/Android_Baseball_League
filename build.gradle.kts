// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.4" apply false
}

buildscript {
    val kotlin_version = "1.9.0"
    val gradle_version = "8.1.2"
    val nav_version = "2.7.4"

    /*
    repositories {
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
    */

    dependencies {
        classpath("com.android.tools.build:gradle:$gradle_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

/*
allprojects {
    repositories {
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}*/