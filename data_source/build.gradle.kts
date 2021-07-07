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
    implementation(Dependencies_Retrofit_Moshi_Converter)
    implementation(Dependencies_PolymorphicJsonAdapterFactory)
    implementation(Dependencies_Retrofit_Logging)

    implementation("com.squareup.moshi:moshi:1.12.0")

    implementation(Dependencies_Firestore)
    implementation(Dependencies_Firebase_Storage)
    implementation(Dependencies_Firestore_Kotlin_Extensions)

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