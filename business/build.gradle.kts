plugins {
    id("java-library")
    kotlin("android")
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
}

sourceCompatibility = "7"
targetCompatibility = "7"
