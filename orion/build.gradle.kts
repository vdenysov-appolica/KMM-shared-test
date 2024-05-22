plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kmmbridge)
    alias(libs.plugins.skie)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    `maven-publish`
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.dateTime)
            implementation(libs.touchlab.kermit)
            implementation(libs.touchlab.stately.concurrency)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
        }
        iosMain.dependencies {
            implementation(libs.touchlab.stately.common)
            implementation(libs.ktor.client.ios)
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    namespace = "co.touchlab.kmmbridgekickstart.orion"
}

addGithubPackagesRepository()

kmmbridge {
    mavenPublishArtifacts()
    spm()
}
