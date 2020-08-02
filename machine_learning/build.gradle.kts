import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("android")
    kotlin("android.extensions")
}

dependencies {
    implementation(Dependencies_Volley)
    implementation(Dependencies_Android_Material)
    implementation(Dependencies_Lifecycle_Arch_Extensions)
    implementation(Dependencies_Android_Annotations)
    implementation(Dependencies_Android_Kotlin_Extensions)
    implementation(Dependencies_Lifecycle_ViewModel)
    implementation(Dependencies_Android_Preference)

    // Barcode model
    implementation(Dependencies_Ml_Kit_Barcode_Scanning)

    // Object feature and model
    implementation(Dependencies_Ml_Kit_Object_Detection)

    // Custom model
    implementation(Dependencies_Ml_Kit_Detection_Custom)

    api(Dependencies_Guava)
}