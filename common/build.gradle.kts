import com.almarai.gradle.dependencies.*

val kotlin_version: String by extra

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("kapt")

    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(Dependencies_Project_Data))

    implementation(Dependencies_Android_Kotlin_Extensions)

    //Firebase
    implementation(Dependencies_Firebase_Analytics)

    implementation(Dependencies_Coroutines_Core)
    implementation(Dependencies_Coroutines_Android)

    //region Machine Learning
    //On Device Text Translation
    implementation(Dependencies_Ml_Kit_On_Device_Text_Translation)
    //endregion

    implementation(Dependencies_Hilt)
    kapt(Dependencies_Hilt_Annotation_Processor)
}
repositories {
    mavenCentral()
}