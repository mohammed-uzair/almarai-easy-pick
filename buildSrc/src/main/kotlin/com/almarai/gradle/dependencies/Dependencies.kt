package com.almarai.gradle.dependencies

//region
const val Dependencies_Version_Kotlin = "1.3.72"

const val Dependencies_Kotlin =
    "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$Dependencies_Version_Kotlin"
const val Dependencies_Kotlin_Reflect =
    "org.jetbrains.kotlin:kotlin-reflect:$Dependencies_Version_Kotlin"


const val Dependencies_Plugin_Gradle = "com.android.tools.build:gradle:4.0.1"

//const val Dependencies_Plugin_Kotlin_Gradle = kotlin("gradle-plugin", "1.3.72")
public const val Dependencies_Plugin_Library = "com.android.library"
const val Dependencies_Plugin_Android = "android"
const val Dependencies_Plugin_My_Custom = "my-plugin"
const val Dependencies_Plugin_Android_Extensions = "android-extensions"
const val Dependencies_Plugin_Jdk_Library = "stdlib-jdk8"

//Android
const val Dependencies_Android_Kotlin_Extensions = "androidx.core:core-ktx:1.3.1"
const val Dependencies_Android_AppCompat = "androidx.appcompat:appcompat:1.1.0"
const val Dependencies_Android_Activity = "androidx.activity:activity-ktx:1.2.0-beta01"
const val Dependencies_Android_Fragment = "androidx.fragment:fragment-ktx:1.3.0-alpha02"
const val Dependencies_Android_Constraint_Layout =
    "androidx.constraintlayout:constraintlayout:2.0.0-beta8"
const val Dependencies_Android_Legacy_support = "androidx.legacy:legacy-support-v4:1.0.0"
const val Dependencies_Android_Preference = "androidx.preference:preference:1.1.1"
const val Dependencies_Android_Annotations = "androidx.annotation:annotation:1.1.0"
const val Dependencies_Android_Material = "com.google.android.material:material:1.3.0-alpha02"
const val Dependencies_Android_Recycler_View = "androidx.recyclerview:recyclerview:1.2.0-alpha05"
const val Dependencies_Android_Paging = "androidx.paging:paging-runtime-ktx:2.1.2"

private const val Dependencies_Version_Lifecycle = "2.3.0-alpha06"
private const val Dependencies_Version_Lifecycle_Extensions = "2.2.0"

const val Dependencies_Lifecycle_Runtime =
    "androidx.lifecycle:lifecycle-runtime:$Dependencies_Version_Lifecycle"
const val Dependencies_Lifecycle_LiveData =
    "androidx.lifecycle:lifecycle-livedata-ktx:$Dependencies_Version_Lifecycle"
const val Dependencies_Lifecycle_ViewModel =
    "androidx.lifecycle:lifecycle-viewmodel-ktx:$Dependencies_Version_Lifecycle"
const val Dependencies_Lifecycle_Extensions =
    "androidx.lifecycle:lifecycle-extensions:$Dependencies_Version_Lifecycle_Extensions"
const val Dependencies_Lifecycle_Arch_Extensions = "android.arch.lifecycle:extensions:1.1.1"
const val Dependencies_Lifecycle_Common = "android.arch.lifecycle:common-java8:1.1.1"

// jetpack navigation components
private const val Dependencies_Version_Navigation = "2.3.0"
const val Dependencies_Navigation =
    "androidx.navigation:navigation-ui-ktx:$Dependencies_Version_Navigation"
const val Dependencies_Navigation_Runtime =
    "androidx.navigation:navigation-runtime:$Dependencies_Version_Navigation"
const val Dependencies_Navigation_Fragment_Kotlin_Extensions =
    "androidx.navigation:navigation-fragment-ktx:$Dependencies_Version_Navigation"

//Coroutines
private const val Dependencies_Version_Coroutines = "1.4.0"
const val Dependencies_Coroutines_Core =
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:$Dependencies_Version_Coroutines"
const val Dependencies_Coroutines_Android =
    "org.jetbrains.kotlinx:kotlinx-coroutines-android:$Dependencies_Version_Coroutines"

/*SDP Library - Use to auto adjust the views for all screen sizes*, only in portrait mode
     url {@link "https://github.com/intuit/sdp"}*/
private const val Dependencies_Version_Sdp = "1.0.6"
const val Dependencies_Sdp = "com.intuit.sdp:sdp-android:$Dependencies_Version_Sdp"

//Retrofit
private const val Dependencies_Version_Retrofit = "2.9.0"

const val Dependencies_Retrofit = "com.squareup.retrofit2:retrofit:$Dependencies_Version_Retrofit"
const val Dependencies_Retrofit_Gson_Converter =
    "com.squareup.retrofit2:converter-gson:$Dependencies_Version_Retrofit"

//DI - Hilt
private const val Dependencies_Version_Hilt = "2.28-alpha"

const val Dependencies_Hilt = "com.google.dagger:hilt-android:$Dependencies_Version_Hilt"
const val Dependencies_Hilt_Annotation_Processor =
    "com.google.dagger:hilt-android-compiler:$Dependencies_Version_Hilt"
const val Dependencies_Hilt_Android_ViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
const val Dependencies_Hilt_Android_Annotation_Processor = "androidx.hilt:hilt-compiler:1.0.0-alpha01"

//Room


//Firebase
private const val Dependencies_Version_Firestore = "21.5.0"
private const val Dependencies_Version_Firebase = "17.4.4"
private const val Dependencies_Version_Firebase_Crashlytics = "17.1.1"
private const val Dependencies_Version_Firebase_Storage = "19.1.1"

const val Dependencies_Firestore =
    "com.google.firebase:firebase-firestore:$Dependencies_Version_Firestore"
const val Dependencies_Firebase_Storage =
    "com.google.firebase:firebase-storage-ktx:$Dependencies_Version_Firebase_Storage"
const val Dependencies_Firestore_Kotlin_Extensions =
    "com.google.firebase:firebase-firestore-ktx:$Dependencies_Version_Firestore"

const val Dependencies_Firebase_Core =
    "com.google.firebase:firebase-core:$Dependencies_Version_Firebase"
const val Dependencies_Firebase_Analytics =
    "com.google.firebase:firebase-analytics-ktx:$Dependencies_Version_Firebase"
const val Dependencies_Firebase_Crashlytics =
    "com.google.firebase:firebase-crashlytics:$Dependencies_Version_Firebase_Crashlytics"

//region Others Dependencies
//Animations - Lottie
private const val Dependencies_Version_Lottie = "3.4.1"
const val Dependencies_Lottie = "com.airbnb.android:lottie:$Dependencies_Version_Lottie"

//Chart - MP Chart
private const val Dependencies_Version_Mp_Chart = "v3.1.0"
const val Dependencies_Mp_Chart = "com.github.PhilJay:MPAndroidChart:$Dependencies_Version_Mp_Chart"

//Network Communication - Volley
private const val Dependencies_Version_Volley = "1.1.1"
const val Dependencies_Volley = "com.android.volley:volley:$Dependencies_Version_Volley"

//Barcode
private const val Dependencies_Version_Ml_Kit = "16.1.0"
private const val Dependencies_Version_Ml_Kit_Barcode_Scanning = "16.0.1"

//On Device Text Translation
private const val Dependencies_Version_Ml_Kit_On_Device_Text_Translation = "16.1.1"

// Barcode model
const val Dependencies_Ml_Kit_Barcode_Scanning =
    "com.google.mlkit:barcode-scanning:$Dependencies_Version_Ml_Kit_Barcode_Scanning"
const val Dependencies_Ml_Kit_On_Device_Text_Translation =
    "com.google.mlkit:translate:$Dependencies_Version_Ml_Kit_On_Device_Text_Translation"

// Object feature and model
const val Dependencies_Ml_Kit_Object_Detection =
    "com.google.mlkit:object-detection:$Dependencies_Version_Ml_Kit"

// Custom model
const val Dependencies_Ml_Kit_Detection_Custom =
    "com.google.mlkit:object-detection-custom:$Dependencies_Version_Ml_Kit"

private const val Dependencies_Version_Guava = "29.0-jre"
const val Dependencies_Guava = "com.google.guava:guava:$Dependencies_Version_Guava"

// Leak Canary
private const val Dependencies_Version_Leak_Canary = "2.0-alpha-3"
const val Dependencies_Leak_Canary =
    "com.squareup.leakcanary:leakcanary-android:$Dependencies_Version_Leak_Canary"
//endregion