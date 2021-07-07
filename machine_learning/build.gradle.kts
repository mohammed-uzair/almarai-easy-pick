import com.almarai.gradle.dependencies.*

//val kotlin_version: String by extra

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("kapt")
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
repositories {
    google()
    mavenCentral()
}