plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("my-plugin")
}

////Locate your keystore file path
//def keystorePropertiesFile = rootProject.file("keystore.properties")
//
////Keystore properties reference
//def keystoreProperties = new Properties()
//
////Assign your keystore file to the keystore reference
//keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
//
//android {
//    compileSdkVersion 29
//    buildToolsVersion "29.0.3"
//
//    defaultConfig {
//        minSdkVersion 23
//        targetSdkVersion 29
//        versionCode 1
//        versionName "1.0"
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles 'consumer-rules.pro'
//    }
//
//    signingConfigs {
//        release {
//            keyAlias keystoreProperties['RELEASE_KEY_ALIAS']
//            keyPassword keystoreProperties['RELEASE_KEY_PASSWORD']
//            storeFile file(keystoreProperties['RELEASE_STORE_FILE'])
//            storePassword keystoreProperties['RELEASE_STORE_PASSWORD']
//        }
//
//        debug{}
//    }
//
//    /*Specify the build types here as release and debug*/
//    buildTypes {
//        Development {
//            signingConfig signingConfigs.debug
//
//            splits.abi.enable = false
//            splits.density.enable = false
//
//            debuggable true
//            minifyEnabled false
//        }
//
//        UAT {
//            signingConfig signingConfigs.release
//
//            splits.abi.enable = false
//            splits.density.enable = false
//
//            debuggable true
//            minifyEnabled true
//
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//
//        release {
//            signingConfig signingConfigs.release
//            debuggable false
//            minifyEnabled true
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = '1.8'
//        targetCompatibility = '1.8'
//    }
//
//    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {
//        kotlinOptions {
//            jvmTarget = "1.8"
//        }
//    }
//}

dependencies {
    val kotlin_version = "1.3.72"
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.1")
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    implementation(project(":data"))

    // -- Retrofit2
    val retrofit2_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit2_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2_version")

    val coroutines_version = "1.3.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")

    implementation("com.google.firebase:firebase-firestore:21.5.0")
    implementation("com.google.firebase:firebase-firestore-ktx:21.5.0")

    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

    val koin_version = "2.1.5"
    // Koin AndroidX Scope features
    implementation("org.koin:koin-androidx-scope:$koin_version")
}
