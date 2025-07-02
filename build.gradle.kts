// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kotlinAndroidKsp) apply false
}
// project-level build.gradle.kts
buildscript {
    dependencies {
        // ... other dependencies
        //classpath(libs.hilt.android.gradle.plugin) // Use the same version as above
    }
}
