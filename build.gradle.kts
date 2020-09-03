import java.net.URI

buildscript {
    repositories {
        jcenter()
        mavenCentral()
        google()
    }

    val kotlinVersion: String by project
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    group = "com.wbrawner.flayre"
    version = "0.1.0"
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        google()
        maven { url = URI("https://kotlin.bintray.com/ktor") }
        maven { url = URI("https://kotlin.bintray.com/kotlinx") }
    }
}
