package com.almarai.gradle.dependencies

//region Test Runners
const val Test_InstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//endregion

//region Unit Test Dependencies
private const val Test_Version_J_Unit4 = "4.12"

const val Test_J_Unit_4 = "junit:junit:$Test_Version_J_Unit4"
//endregion

//region Instrumentation Test Dependencies
private const val Test_Instrumentation_Version_J_Unit = "1.1.1"
private const val Test_Instrumentation_Version_Espresso = "3.2.0"

const val Test_Instrumentation_J_Unit =
    "androidx.test.ext:junit:$Test_Instrumentation_Version_J_Unit"
const val Test_Instrumentation_Espresso =
    "androidx.test.espresso:espresso-core:$Test_Instrumentation_Version_Espresso"
//endregion