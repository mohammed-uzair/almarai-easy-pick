import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.application")
    id("my-plugin")
    kotlin("android")
    kotlin("android.extensions")
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
    implementation(project(Dependencies_Project_Machine_Learning))
    implementation(project(Dependencies_Project_Alm_Logging))
    implementation(project(Dependencies_Project_Repository))
    implementation(project(Dependencies_Project_Alm_Ui))
    implementation(project(Dependencies_Project_Business))
    implementation(project(Dependencies_Project_Data))

    implementation(Dependencies_Sdp)
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Lottie)
    implementation(Dependencies_Android_Kotlin_Extensions)
    implementation(Dependencies_Android_Paging)
    implementation(Dependencies_Android_Constraint_Layout)
    implementation(Dependencies_Android_Legacy_support)
    implementation(Dependencies_Lifecycle_Extensions)
    implementation(Dependencies_Lifecycle_ViewModel)
    implementation(Dependencies_Android_Preference)
    implementation(Dependencies_Firebase_Core)
    implementation(Dependencies_Navigation_Fragment_Kotlin_Extensions)
    implementation(Dependencies_Navigation)

//    implementation(Dependencies_Koin_Scope)
//    implementation(Dependencies_Koin_ViewModel)
//    implementation(Dependencies_Koin_Fragment)

    implementation(Dependencies_Hilt)
    implementation(Dependencies_Hilt_Android_ViewModel)
    implementation(project(mapOf("path" to ":data_source")))
    kapt(Dependencies_Hilt_Annotation_Processor)
    kapt(Dependencies_Hilt_Android_Annotation_Processor)

    implementation(Dependencies_Retrofit)
    implementation(Dependencies_Retrofit_Gson_Converter)
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

    //implementation(Dependencies_Leak_Canary)
}
