import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.5.31"
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "me.ghostbear.composewaifu"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")

    // Compose
    implementation("androidx.compose.runtime:runtime:1.0.5")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.compose.ui:ui:1.0.5")
    implementation("androidx.compose.ui:ui-tooling:1.0.5")
    implementation("androidx.compose.foundation:foundation:1.0.5")
    implementation("androidx.compose.material:material:1.0.5")
    implementation("androidx.compose.material:material-icons-core:1.0.5")
    implementation("androidx.compose.material:material-icons-extended:1.0.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")

    // Ktor
    val ktorVersion = "1.6.7"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    // Image Loading
    implementation("io.coil-kt:coil-compose:1.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}

kapt {
    correctErrorTypes = true
}