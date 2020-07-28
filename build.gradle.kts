buildscript {
    val NAVIGATION_VESION = "2.3.0-rc01"

    //Project level repositories
    allprojects {
        repositories {
            google()
            jcenter()
        }
    }

    dependencies {
        //Gradle
        classpath(BuildPlugins.androidGradlePlugin)

        //Kotlin
        classpath(BuildPlugins.kotlinGradlePlugin)

        //Google Services
        classpath("com.google.gms:google-services:4.3.3")

        //Crashlytics
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.2.0")

        //Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VESION")
    }
}

//Global repositories
allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean").configure { delete("build") }

//fun getRepositories() =
//    repositories {
//        google()
//        jcenter()
//        maven { url = uri("https://jitpack.io") }
//    }