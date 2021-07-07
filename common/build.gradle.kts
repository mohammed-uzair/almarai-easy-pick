import com.almarai.gradle.dependencies.*

//val kotlin_version: String by extra

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