buildscript {
    val kotlin_version = "1.4.31"
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.google.gms:google-services:4.3.5")

        //Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")

        // Add the Crashlytics Gradle plugin.
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.5.2")

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.4")
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