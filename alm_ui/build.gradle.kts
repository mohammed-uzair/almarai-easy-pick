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
////    resourcePrefix 'alm_ui_'
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
    /*SDP Library - Use to auto adjust the views for all screen sizes*, only in portrait mode
     url {@link "https://github.com/intuit/sdp"}*/
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.1")
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
