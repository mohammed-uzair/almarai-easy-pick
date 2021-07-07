buildscript {
//    val kotlin_version = "1.4.31"
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath("com.google.gms:google-services:4.3.8")

        //Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.37")

        // Add the Crashlytics Gradle plugin.
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}