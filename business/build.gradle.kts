plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
}

//plugins {
//    id("com.android.library")
//    kotlin("android")
//    kotlin("android.extensions")
//    id("my-plugin")
//}
//
//apply plugin: 'java-library'
//apply plugin: 'kotlin'
//
//dependencies {
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
//}
//
//sourceCompatibility = "7"
//targetCompatibility = "7"