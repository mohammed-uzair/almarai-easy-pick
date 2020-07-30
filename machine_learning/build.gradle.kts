plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
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
//        debug {}
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
//
//    aaptOptions {
//        noCompress "tflite"
//    }
//
//    sourceSets.main {
//        assets.srcDirs = ['assets']
//    }
//}
//
//androidExtensions {
//    experimental = true
//}

dependencies {
    implementation("com.android.volley:volley:1.1.1")
    implementation("com.google.android.material:material:1.3.0-alpha02")
    implementation("android.arch.lifecycle:extensions:1.1.1")
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.preference:preference:1.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")

    // Barcode model
    implementation("com.google.mlkit:barcode-scanning:16.0.1")

    // Object feature and model
    implementation("com.google.mlkit:object-detection:16.1.0")

    // Custom model
    implementation("com.google.mlkit:object-detection-custom:16.1.0")

    api("com.google.guava:guava:29.0-jre")
}