plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

repositories {
    jcenter()
    mavenCentral()
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "0.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
        }
        getByName("release") {
        }
    }
}

val ktorVersion: String by project
val logbackVersion: String by project

dependencies {
    implementation(project(":shared"))
    implementation("io.ktor:ktor-client-android:$ktorVersion")
}

tasks.getByName("check").dependsOn("lint")
