import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("kapt")
}

dependencies {
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)

    implementation(com.almarai.gradle.dependencies.Dependencies_Room)
    kapt(com.almarai.gradle.dependencies.Dependencies_Room_Compiler)

    implementation(Dependencies_Coroutines_Core)
}
repositories {
    mavenCentral()
}