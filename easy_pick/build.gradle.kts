import com.almarai.gradle.dependencies.*

//val kotlin_version: String by extra

plugins {
    id("com.android.application")
    id("my-plugin")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")

    id("dagger.hilt.android.plugin")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(Dependencies_Project_Common))
    implementation(project(Dependencies_Project_Machine_Learning))
    implementation(project(Dependencies_Project_Repository))
    implementation(project(Dependencies_Project_Alm_Ui))
    implementation(project(Dependencies_Project_Business))
    implementation(project(Dependencies_Project_Data))
    implementation(project(Dependencies_Project_Data_Source))

    implementation(Dependencies_Sdp)
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Lottie)
    implementation(Dependencies_Android_Kotlin_Extensions)
    implementation(Dependencies_Android_Paging)
    implementation(Dependencies_Android_Constraint_Layout)
//    implementation(Dependencies_Android_Activity)
//    implementation(Dependencies_Android_Fragment)
    implementation(Dependencies_Android_Legacy_support)
    implementation(Dependencies_Lifecycle_Extensions)
    implementation(Dependencies_Lifecycle_ViewModel)
    implementation(Dependencies_Android_Preference)
    implementation(Dependencies_Firebase_Core)
    implementation(Dependencies_Navigation_Fragment_Kotlin_Extensions)

    implementation(Dependencies_Hilt)
    kapt(Dependencies_Hilt_Annotation_Processor)

    implementation(Dependencies_Hilt_Android_ViewModel)
    kapt(Dependencies_Hilt_Android_Annotation_Processor)

    implementation(Dependencies_Lifecycle_Runtime)
    implementation(Dependencies_Lifecycle_Common)
    implementation(Dependencies_Coroutines_Core)
    implementation(Dependencies_Coroutines_Android)
    implementation(Dependencies_Lifecycle_LiveData)
    implementation(Dependencies_Firebase_Analytics)
    implementation(Dependencies_Firebase_Crashlytics)
    implementation(Dependencies_Navigation_Fragment_Kotlin_Extensions)
    implementation(Dependencies_Navigation)
    implementation(Dependencies_Navigation_Runtime)
    implementation(Dependencies_Android_Material)
    implementation(Dependencies_Android_Recycler_View)
    implementation(Dependencies_Mp_Chart)

    implementation(Dependencies_Ml_Kit_On_Device_Text_Translation)

    implementation ("com.squareup.moshi:moshi:1.12.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")

    implementation (Dependencies_Retrofit)
    implementation (Dependencies_Retrofit_Moshi_Converter)

    //implementation(Dependencies_Leak_Canary)
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