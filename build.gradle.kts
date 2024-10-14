// Top-level build file where you can add configuration options common to all sub-projects/modules.

//buil.grade.kts --> nivel proyecto

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.compose.compiler) apply false

}