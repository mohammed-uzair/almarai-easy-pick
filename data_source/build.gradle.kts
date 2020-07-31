import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

dependencies {
    implementation(project(Dependencies_Project_Data))

    implementation(Dependencies_Kotlin_Reflect)

    implementation(Dependencies_Coroutines_Core)

    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)

    implementation(Dependencies_Retrofit)
    implementation(Dependencies_Retrofit_Gson_Converter)

    implementation(Dependencies_Firestore)
    implementation(Dependencies_Firestore_Kotlin_Extensions)

    implementation(Dependencies_Koin_Scope)
}