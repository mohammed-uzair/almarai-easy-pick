import com.almarai.gradle.dependencies.*

val kotlin_version: String by extra

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("kapt")

    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(Dependencies_Project_Common))

    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)

    implementation(Dependencies_Android_Paging)
    implementation(project(Dependencies_Project_Data))
    implementation(project(Dependencies_Project_Data_Source))
    implementation(project(Dependencies_Project_Business))

    implementation(Dependencies_Coroutines_Core)
    implementation(Dependencies_Coroutines_Android)

    implementation(Dependencies_Hilt)
    kapt(Dependencies_Hilt_Annotation_Processor)
}
repositories {
    mavenCentral()
}