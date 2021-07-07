plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("my-plugin") {
            id = "my-plugin"
            implementationClass = "com.almarai.gradle.MyModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:4.2.2")
    implementation(kotlin("gradle-plugin", "1.5.20"))
    implementation(kotlin("android-extensions"))
    implementation(kotlin("stdlib-jdk8"))
}