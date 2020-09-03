plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir("src")
        resources.srcDir("resources")
    }
    test {
        java.srcDir("test")
        resources.srcDir("testresources")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
