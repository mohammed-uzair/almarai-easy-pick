import com.almarai.gradle.dependencies.Dependencies_Android_AppCompat
import com.almarai.gradle.dependencies.Dependencies_Android_Kotlin_Extensions
import com.almarai.gradle.dependencies.Dependencies_Sdp

plugins {
    id("com.android.library")
    id("my-plugin")
}

dependencies {
    implementation(project(com.almarai.gradle.dependencies.Dependencies_Project_Common))

    implementation(Dependencies_Sdp)
    implementation(Dependencies_Android_AppCompat)
    implementation(Dependencies_Android_Kotlin_Extensions)
}
repositories {
    mavenCentral()
}