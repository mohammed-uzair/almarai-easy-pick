import com.almarai.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("my-plugin")
    kotlin("kapt")
}

dependencies {
    implementation(Dependencies_Kotlin_Reflect)
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)

    implementation(Dependencies_Retrofit_Moshi_Converter)

    implementation("com.squareup.moshi:moshi:1.12.0")
    implementation(Dependencies_PolymorphicJsonAdapterFactory)
}
repositories {
    google()
    mavenCentral()
}