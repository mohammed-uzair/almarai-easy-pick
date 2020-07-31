package com.almarai.gradle.dependencies

const val APP_NAME = "Easy Pick"

//region Build Versions
const val APP_VERSION_MAJOR = 1
const val APP_VERSION_MINOR = 0
const val APP_VERSION_PATCH = 0

//DO NOT USE OR CHANGE
val APP_VERSION_CODE: Int = "$APP_VERSION_MAJOR$APP_VERSION_MINOR$APP_VERSION_PATCH".toInt()
const val APP_VERSION_NAME: String = "$APP_VERSION_MAJOR.$APP_VERSION_MINOR.$APP_VERSION_PATCH"
//endregion

//region SDK Version
const val COMPILE_SDK_VERSION = 29
const val TARGET_SDK_VERSION = 29
const val MINIMUM_SDK_VERSION = 23
//endregion

//region Proguard
const val PROGUARD_FILE = "proguard-android-optimize.txt"
const val PROGUARD_RULES_FILE = "proguard-rules.pro"
//endregion