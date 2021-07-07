import com.almarai.gradle.dependencies.*

//val kotlin_version: String by extra

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

kapt {
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }

    correctErrorTypes = true
}

repositories {
    google()
    mavenCentral()
}