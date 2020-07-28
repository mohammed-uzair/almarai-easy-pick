plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")
}

//Locate your keystore file path
def keystorePropertiesFile = rootProject.file("keystore.properties")

//Keystore properties reference
def keystoreProperties = new Properties()

//Assign your keystore file to the keystore reference
keystoreProperties.load(new FileInputStream(keystorePropertiesFile))

android {
    def appName = "Easy Pick"

    compileSdkVersion 29
    buildToolsVersion "29.0.3"

    defaultConfig {
        //Creates a placeholder property for common application name and intent to use in the manifest.
        manifestPlaceholders = [appName: appName]

        //Add a new build config field for common application name into the BuildConfig class.
        buildConfigField("String", "APP_NAME", "\"${appName}\"")

        applicationId "com.almarai.easypick"
        minSdkVersion 23
        targetSdkVersion 29
        versionCode 1
        versionName "1.0.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    dataBinding {
        enabled true
    }

    signingConfigs {
        release {
            keyAlias keystoreProperties['RELEASE_KEY_ALIAS']
            keyPassword keystoreProperties['RELEASE_KEY_PASSWORD']
            storeFile file(keystoreProperties['RELEASE_STORE_FILE'])
            storePassword keystoreProperties['RELEASE_STORE_PASSWORD']
        }

        debug {}
    }

    /*Specify the build types here as release and debug*/
    buildTypes {
        //Modify the output package name(Should be like -> SARASLite_debug_100310)
        applicationVariants.all { variant ->
            variant.outputs.all {
                outputFileName =
                        "${appName}_${variant.buildType.name}_${defaultConfig.versionName}.apk"
            }
        }

        Development {
            signingConfig signingConfigs.debug

            splits.abi.enable = false
            splits.density.enable = false

            debuggable true
            shrinkResources false
            minifyEnabled false
        }

        UAT {
            signingConfig signingConfigs.release

            splits.abi.enable = false
            splits.density.enable = false

            debuggable true
            shrinkResources true
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }

        release {
            signingConfig signingConfigs.release
            debuggable false
            shrinkResources true
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility = '1.8'
        targetCompatibility = '1.8'
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    aaptOptions {
        noCompress "tflite"
    }

    /* Some times, when javac fails due to Dagger, the entire gradle build halts,
     * and therefore you get errors crossing 100, most of these errors are not actually errors.
     * we cannot be able to see the actual error, thus increasing the XmaXerrs counts will show us more
     * errors in the terminal and thus our actual error can also be found there
     * For now we are increasing the max error output buffer to 500*/
    allprojects {
        afterEvaluate {
            tasks.withType(JavaCompile.class) {
                options.compilerArgs << "-Xmaxerrs" << "500"
            }
        }
    }

    configurations {
        all*.exclude group: 'com.google.guava', module: 'listenablefuture'
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    /*SDP Library - Use to auto adjust the views for all screen sizes*, only in portrait mode
     url {@link "https://github.com/intuit/sdp"}*/
    implementation "com.intuit.sdp:sdp-android:1.0.6"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'com.airbnb.android:lottie:3.4.1'
    implementation 'androidx.core:core-ktx:1.3.0'
    //Pagination
    implementation 'androidx.paging:paging-runtime-ktx:2.1.2'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.0-beta8'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
    implementation 'androidx.preference:preference:1.1.1'
    implementation 'com.google.firebase:firebase-core:17.4.4'
    implementation project(path: ':machine_learning')
    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.0'
    implementation project(path: ':alm_logging')
    implementation project(path: ':repository')
    implementation project(path: ':alm_ui')
    implementation project(path: ':business')

    implementation 'com.daimajia.easing:library:2.1@aar'
    implementation 'com.daimajia.androidanimations:library:2.3@aar'
    implementation project(path: ':data')


    // latest stable
    def koin_version = '2.1.5'
    // Koin AndroidX Scope features
    implementation "org.koin:koin-androidx-scope:$koin_version"
// Koin AndroidX ViewModel features
    implementation "org.koin:koin-androidx-viewmodel:$koin_version"
// Koin AndroidX Fragment features
    implementation "org.koin:koin-androidx-fragment:$koin_version"

    // -- Retrofit2
    def retrofit2_version = '2.9.0'
    implementation "com.squareup.retrofit2:retrofit:$retrofit2_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit2_version"

    // -- Lifecycle Components (ViewModel, LiveData and ReactiveStreams)
    def lifecycle_version = '2.3.0-alpha05'
    implementation "androidx.lifecycle:lifecycle-runtime:$lifecycle_version"
    kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    // -- Coroutines
    def coroutines_version = "1.3.4"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    // LiveData Coroutines
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    // Recommended: Add the Firebase SDK for Google Analytics.
    implementation 'com.google.firebase:firebase-analytics-ktx:17.4.4'

    // Add the Firebase Crashlytics SDK.
    implementation 'com.google.firebase:firebase-crashlytics:17.1.1'

    // jetpack navigation components
    def nav_version = '2.3.0'
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    implementation "androidx.navigation:navigation-runtime:$nav_version"

    def material_version = '1.3.0-alpha01'
    implementation "com.google.android.material:material:$material_version"

    // Leak Canary
//    def leak_canary_version = "2.0-alpha-3"
//    debugImplementation "com.squareup.leakcanary:leakcanary-android:$leak_canary_version"

    // Recyclerview
    def recyclerview_version = '1.2.0-alpha04'
    implementation "androidx.recyclerview:recyclerview:$recyclerview_version"

    //Chart
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

    // Barcode model
    implementation 'com.google.mlkit:barcode-scanning:16.0.1'

    api 'com.google.guava:guava:29.0-jre'

    //Round view
    implementation 'de.hdodenhof:circleimageview:3.1.0'
}
