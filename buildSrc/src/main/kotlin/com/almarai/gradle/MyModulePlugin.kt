package com.almarai.gradle

import com.almarai.gradle.dependencies.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.set

class MyModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Apply Required Plugins.
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-android-extensions")

        // Configure common android build parameters.
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            androidExtension.apply {
                when (this) {
                    is LibraryExtension -> defaultConfig {
                        consumerProguardFiles(PROGUARD_RULES_FILE)
                    }
                    is AppExtension -> buildTypes {
                        getByName(Release) {
                            isMinifyEnabled = true
                            isShrinkResources = true
                            proguardFiles(
                                getDefaultProguardFile(PROGUARD_FILE),
                                PROGUARD_RULES_FILE
                            )
                        }
                    }
                }

                //App signing configurations
                signingConfigs {
                    create(Release) {
                        keyAlias = "RELEASE_KEY_ALIAS"
                        keyPassword = "RELEASE_KEY_PASSWORD"
                        storePassword = "RELEASE_STORE_PASSWORD"
                        storeFile =
                            File("/Users/uzair/Workspace/Projects/Android/AndroidStudio/MyWork/EasyPick/keystore.properties")
                    }
                }

                /*variantFilter {
                    buildOutputs.all {
                        //Easy Pick_release_2.0.1_20_Dec_2020_08_10_34.apk
//                            named("${APP_NAME}_${buildType.name}_${defaultConfig.versionName.trim()}_${getCurrentDateTime()}.apk")

                        if (outputFile.name.contains(".apk")) {
                            outputFile.renameTo(File("${APP_NAME}_${buildType.name}_${defaultConfig.versionName.trim()}_${getCurrentDateTime()}.apk"))
                        }
                    }
                }*/

                //Build types
                buildTypes {
                    getByName(Debug) {
                        signingConfig = signingConfigs.getByName(Debug)

                        splits.abi.isEnable = false

                        isDebuggable = true
                    }

                    getByName(Release) {
                        signingConfig = signingConfigs.getByName(Release)
                        isDebuggable = false
                    }

                    create(UAT) {
                        signingConfig = signingConfigs.getByName(Release)

                        splits.abi.isEnable = false

                        isDebuggable = true

                        proguardFiles(
                            getDefaultProguardFile(PROGUARD_FILE),
                            PROGUARD_RULES_FILE
                        )
                    }
                }

                compileSdkVersion(COMPILE_SDK_VERSION)

                defaultConfig {
                    targetSdkVersion(TARGET_SDK_VERSION)
                    minSdkVersion(MINIMUM_SDK_VERSION)

                    versionCode = APP_VERSION_CODE
                    versionName = APP_VERSION_NAME

                    //Creates a placeholder property for common application name and intent to use in the manifest.
                    manifestPlaceholders["appName"] = APP_NAME

                    //Add a new build config field for common application name into the BuildConfig class.
                    buildConfigField("String", "APP_NAME", "\"$APP_NAME\"")

                    testInstrumentationRunner = Test_InstrumentationRunner
                }

                // Java 8
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                project.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "1.8"
                    }
                }
            }
        }

        // Adds required dependencies for all modules.
        project.dependencies {
            //Default Kotlin Dependencies
            add("implementation", Dependencies_Kotlin)

            //Default Test Dependencies
            add("testImplementation", Test_J_Unit_4)
        }
    }
}