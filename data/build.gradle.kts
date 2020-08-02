import com.almarai.gradle.dependencies.Dependencies_Android_AppCompat
import com.almarai.gradle.dependencies.Dependencies_Android_Kotlin_Extensions
import com.almarai.gradle.dependencies.Dependencies_Kotlin_Reflect

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("android")
    kotlin("android.extensions")
}

dependencies {
    implementation(Dependencies_Kotlin_Reflect)
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)
}