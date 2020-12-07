import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(Dependencies_Project_Common))
    implementation(project(Dependencies_Project_Data))
    implementation(project(Dependencies_Project_Business))

    implementation(Dependencies_Kotlin_Reflect)

    implementation(Dependencies_Coroutines_Core)

    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)

    implementation(Dependencies_Retrofit)
    implementation(Dependencies_Retrofit_Gson_Converter)

    implementation(Dependencies_Firestore)
    implementation(Dependencies_Firebase_Storage)
    implementation(Dependencies_Firestore_Kotlin_Extensions)

    implementation(Dependencies_Hilt)
    kapt(Dependencies_Hilt_Annotation_Processor)
}
repositories {
    mavenCentral()
}