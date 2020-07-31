import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("android")
    kotlin("android.extensions")
}

dependencies {
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)

    implementation(Dependencies_Android_Paging)
    implementation(project(Dependencies_Project_Data))
    implementation(project(Dependencies_Project_Data_Source))
    implementation(project(Dependencies_Project_Business))

    implementation(Dependencies_Coroutines_Core)
    implementation(Dependencies_Coroutines_Android)
    implementation(Dependencies_Koin_Scope)
}
